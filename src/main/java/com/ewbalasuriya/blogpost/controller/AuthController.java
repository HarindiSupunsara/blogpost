package com.ewbalasuriya.blogpost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ewbalasuriya.blogpost.dto.req.AuthRequestDto;
import com.ewbalasuriya.blogpost.dto.req.RegisterRequestDto;
import com.ewbalasuriya.blogpost.dto.resp.CommonResponseDto;
import com.ewbalasuriya.blogpost.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody AuthRequestDto authRequestDto) throws Exception{
		try {
			CommonResponseDto responseDto = userService.login(authRequestDto);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody RegisterRequestDto registerRequestDto) throws Exception{
		try {
			CommonResponseDto responseDto = userService.register(registerRequestDto);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
