package com.ewbalasuriya.blogpost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ewbalasuriya.blogpost.dto.req.PasswordChangeRequestDto;
import com.ewbalasuriya.blogpost.dto.resp.CommonResponseDto;
import com.ewbalasuriya.blogpost.service.UserService;

@RestController
@RequestMapping("/password")
@CrossOrigin(origins = "*")
public class PasswordController {
	
	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Object> changePassword(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto) {
		try {
			CommonResponseDto responseDto = userService.changePassword(passwordChangeRequestDto);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
}
