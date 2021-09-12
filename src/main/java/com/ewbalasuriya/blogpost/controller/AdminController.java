package com.ewbalasuriya.blogpost.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ewbalasuriya.blogpost.dto.req.RegisterRequestDto;
import com.ewbalasuriya.blogpost.dto.resp.CommonResponseDto;
import com.ewbalasuriya.blogpost.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService; 
	
	@PostMapping
	public ResponseEntity<Object> adminRegister(@RequestBody RegisterRequestDto registerRequestDto) throws Exception{
		try {
			CommonResponseDto responseDto = userService.adminRegister(registerRequestDto);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<Object> getAll() {
		try {
			List<RegisterRequestDto> responseDto = userService.getAllAdmins();
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> get(@PathVariable Integer id) {
		try {
			RegisterRequestDto responseDto = userService.getAdmin(id);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/change-status/{id}/{status}")
	private ResponseEntity<Object> changeStatus(@PathVariable Integer id,@PathVariable Integer status ){
		try {
			CommonResponseDto commonResponseDto= userService.changeStatus(id, status);
			return new ResponseEntity<Object>(commonResponseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-count")
	private ResponseEntity<Object> getResultCount(){
		try {
			CommonResponseDto commonResponseDto= userService.getResultCount();
			return new ResponseEntity<Object>(commonResponseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
