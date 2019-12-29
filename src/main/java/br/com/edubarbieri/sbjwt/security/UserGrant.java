package br.com.edubarbieri.sbjwt.security;

import org.springframework.security.core.GrantedAuthority;

import br.com.edubarbieri.sbjwt.entity.Role;

public class UserGrant implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	private final String grant;
	
	public UserGrant(Role authority) {
		this.grant = authority.getId();
	}
	
	@Override
	public String getAuthority() {
		return grant;
	}

}
