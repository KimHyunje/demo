package com.demo.booksearch.user.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.booksearch.user.application.UserApplicationService;
import com.demo.booksearch.user.interfaces.dto.UserRegisterRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/users")
public class UserRestControllerV1 {

	private final UserApplicationService applicationService;


	public UserRestControllerV1(UserApplicationService userApplicationService) {
		this.applicationService = userApplicationService;
	}


	@PostMapping
	public ResponseEntity<?> register(@RequestBody UserRegisterRequest requestBody) {
		log.info("requestBody: [{}]", requestBody);
		var id = requestBody.getId();
		var plainPassword = requestBody.getPassword();
		applicationService.register(id, plainPassword);
		var responseEntity = ResponseEntity.ok().build();
		log.info("responseEntity: [{}]", responseEntity);
		return responseEntity;
	}

}
