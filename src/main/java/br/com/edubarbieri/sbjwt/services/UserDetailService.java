package br.com.edubarbieri.sbjwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.edubarbieri.sbjwt.repository.UserRepository;
import br.com.edubarbieri.sbjwt.security.AuthUserDetails;

@Service
public class UserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByLogin(username);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("Not found user with username " + username);
		}
		return new AuthUserDetails(user.get());
	}

}
