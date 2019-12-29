package br.com.edubarbieri.sbjwt.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloController {
    
	
	@GetMapping({ "/test" })
    public String test(Authentication  user){
		log.info("{}", user.getPrincipal());
        return "Hello Word";
    }
}
