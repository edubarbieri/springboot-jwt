package br.com.edubarbieri.sbjwt.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.edubarbieri.sbjwt.services.JWTService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private UserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		tokenLogin(request);
		filterChain.doFilter(request, response);
	}
	
	private void tokenLogin(HttpServletRequest request) {
		final var authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			return;
		}
		
		var token = authHeader.substring(7);
		if (!jwtService.validateToken(token)) {
			return;
		}
		
		String login = jwtService.extractLogin(token);
		if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			var userDetails = userDetailService.loadUserByUsername(login);
			var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
	}

}
