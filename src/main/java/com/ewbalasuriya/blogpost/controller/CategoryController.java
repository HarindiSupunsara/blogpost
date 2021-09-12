package com.ewbalasuriya.blogpost.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ewbalasuriya.blogpost.dto.resp.CategoryResponceDto;
import com.ewbalasuriya.blogpost.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<Object> getAll() {
		try {
			List<CategoryResponceDto> responseDto = categoryService.getAllActive();
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

}
