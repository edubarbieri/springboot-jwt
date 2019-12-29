package br.com.edubarbieri.sbjwt.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edubarbieri.sbjwt.entity.User;
import br.com.edubarbieri.sbjwt.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public ResponseEntity<Collection<User>> getAll() {
		return ResponseEntity.ok(userRepository.findAll());
	}

}
