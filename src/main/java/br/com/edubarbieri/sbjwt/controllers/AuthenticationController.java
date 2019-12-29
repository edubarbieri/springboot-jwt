package br.com.edubarbieri.sbjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edubarbieri.sbjwt.model.AuthenticationRequest;
import br.com.edubarbieri.sbjwt.model.AuthenticationResponse;
import br.com.edubarbieri.sbjwt.services.JWTService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping
	public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) throws Exception {
		try {
			var authReq = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
			Authentication authenticate = authenticationManager.authenticate(authReq);
			Object principal = authenticate.getPrincipal();
			final var token = jwtService.generateToken((UserDetails) principal);
			return ResponseEntity.ok(new AuthenticationResponse(token));
		} catch (DisabledException |LockedException | BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username or Password");
		} 
	}
}
