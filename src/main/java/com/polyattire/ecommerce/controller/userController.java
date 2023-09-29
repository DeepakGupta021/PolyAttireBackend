package com.polyattire.ecommerce.controller;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polyattire.ecommerce.entity.EcomRole;
import com.polyattire.ecommerce.entity.EcomUser;
import com.polyattire.ecommerce.service.UserService;
import com.polyattire.ecommerce.utility.AES;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("http://localhost:4200")
public class userController {

	@Autowired
	UserService service;
	
	@Value("${encoder.secret.value}")
	private String secret;
	
	@Value("${aes.secret.value}")
	private String tokenAESKey;

	@GetMapping("/api/users")
	public ResponseEntity<List<EcomUser>> getUsers()
	{
		return new ResponseEntity<List<EcomUser>>(service.getUser(),null,200);
	}
	
	@PostMapping(path = "/api/user/save",consumes = "application/json", produces = "application/json")
	public ResponseEntity<EcomUser> saveUser(@RequestBody EcomUser ecomUser)
	{
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return ResponseEntity.created(uri).body(service.saveUser(ecomUser));	
	}
	
	@PostMapping(path = "/api/role/save",consumes = "application/json", produces = "application/json")
	public ResponseEntity<EcomRole> saveRole(@RequestBody EcomRole ecomRole)
	{
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(service.saveRole(ecomRole));
	}
	
	@PostMapping(path = "api/role/addroletouser",consumes = "application/json")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
		service.attachRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "api/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String authorizationHeader =  request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				refresh_token = AES.decrypt(refresh_token, tokenAESKey);
				Algorithm algorithm =  Algorithm.HMAC256(secret.getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				EcomUser user = service.getUserByUsername(username);
				
				String access_token =  JWT.create()
						.withSubject(user.getEmail())
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles", user.getRoles().stream().map(EcomRole::getName).collect(Collectors.toList()))
						.withExpiresAt(new Date(System.currentTimeMillis() +  10 * 60 * 1000))
						.sign(algorithm);
				Map<String, String> tokens = new HashMap<String, String>();
				tokens.put("access_token", AES.encrypt(access_token, tokenAESKey));
				tokens.put("refresh_token", AES.encrypt(refresh_token, tokenAESKey));
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			
			} catch (Exception e) {
				log.error("Error logging in: {}",e.getMessage());
				response.setHeader("error", e.getMessage());
				response.setStatus(FORBIDDEN.value());
				//response.sendError(FORBIDDEN.value());
				Map<String, String> error = new HashMap<String, String>();
				error.put("error_message", e.getMessage());
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), error);
				
			}
			
		}else {
			throw new RuntimeException("Refresh token is missing");
		}
	}
}

@Data
class RoleToUserForm{
	private String username;
	private String roleName;
}
