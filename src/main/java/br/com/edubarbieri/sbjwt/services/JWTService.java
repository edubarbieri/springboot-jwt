package br.com.edubarbieri.sbjwt.services;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JWTService {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.jwtExpirationTime}")
	private long jwtExpirationTime;
	
	public String generateToken(UserDetails userDetails) {
		return createToken(Collections.emptyMap(), userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.addClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
				.parseClaimsJws(token)
				.getBody();
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final var claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractLogin(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpied(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public boolean validateToken(String token) {
		try {
			return !isTokenExpied(token);
		} catch (SignatureException e) {
			log.debug("Invalid signature for token {}", token); 		
		}
		return false;
	}
}
