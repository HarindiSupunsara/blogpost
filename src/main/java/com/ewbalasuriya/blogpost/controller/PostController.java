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

import com.ewbalasuriya.blogpost.dto.req.PostRequestDto;
import com.ewbalasuriya.blogpost.dto.resp.CommonResponseDto;
import com.ewbalasuriya.blogpost.service.PostService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody PostRequestDto postRequestDto) {
		try {
			CommonResponseDto responseDto = postService.createPost(postRequestDto);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping
	public ResponseEntity<Object> getAll() {
		try {
			List<PostRequestDto> responseDto = postService.getAll();
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Object> get(@PathVariable Integer id) {
		try {
			PostRequestDto responseDto = postService.get(id);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/approved/{status}")
	public ResponseEntity<Object> getAllApproved(@PathVariable String status) {
		try {
			List<PostRequestDto> responseDto = postService.getAllApproved(status);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<Object> getAllByCategory(@PathVariable Integer id) {
		try {
			List<PostRequestDto> responseDto = postService.getAllByCategory(id);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getByUser(@PathVariable Integer id) {
		try {
			List<PostRequestDto> responseDto = postService.getByUser(id);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/approve-status/{id}/{adminId}/{status}")
	public ResponseEntity<Object> changeApproveStatus(@PathVariable Integer id, @PathVariable Integer adminId, @PathVariable String status) {
		try {
			CommonResponseDto responseDto= postService.changeApproveStatus(id, adminId, status);
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/change-status/{id}/{status}")
	private ResponseEntity<Object> changeStatus(@PathVariable Integer id,@PathVariable Integer status ){
		try {
			CommonResponseDto commonResponseDto= postService.changeStatus(id, status);
			return new ResponseEntity<Object>(commonResponseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
