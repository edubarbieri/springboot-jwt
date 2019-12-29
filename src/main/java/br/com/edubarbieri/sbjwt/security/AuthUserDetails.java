package br.com.edubarbieri.sbjwt.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import br.com.edubarbieri.sbjwt.entity.User;

public class AuthUserDetails implements org.springframework.security.core.userdetails.UserDetails{

	private static final long serialVersionUID = 1L;
	
	private final String password;
	private final String userName;
	private final boolean enabled;
	private final Set<UserGrant> authorities;
	
	
	public AuthUserDetails(User user) {
		this.password = user.getPassword();
		this.userName = user.getLogin();
		this.enabled = Boolean.TRUE.equals(user.getEnabled());
		if(user.getRoles() == null || user.getRoles().isEmpty()) {
			this.authorities = Collections.emptySet();
		}else {
			this.authorities = user.getRoles().stream()
				.map(UserGrant::new)
				.collect(Collectors.toSet());
				
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String toString() {
		return "AuthUserDetails [userName=" + userName + ", enabled=" + enabled + ", authorities=" + authorities + "]";
	}
	
	


}
