package com.ewbalasuriya.blogpost.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewbalasuriya.blogpost.dto.resp.CategoryResponceDto;
import com.ewbalasuriya.blogpost.entity.CategoryEntity;
import com.ewbalasuriya.blogpost.repository.CategoryRepository;
import com.ewbalasuriya.blogpost.service.CategoryService;
import com.ewbalasuriya.blogpost.util.AppConstant;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryResponceDto> getAllActive() throws Exception {
		 List<CategoryResponceDto> categoryResponceDtos = new ArrayList<CategoryResponceDto>();
		List<CategoryEntity> entities = categoryRepository.findByStatus(AppConstant.ACTIVE);
		if(entities!= null && !entities.isEmpty()) {
			entities.forEach(e-> {
				categoryResponceDtos.add(getCategoryResponceDto(e));
			});
			
		}
		
		return categoryResponceDtos;
	}

	@Override
	public CategoryResponceDto getCategoryResponceDto(CategoryEntity e) {
		CategoryResponceDto dto = new CategoryResponceDto();
		dto.setDescription(e.getDescription());
		dto.setId(e.getId());
		dto.setName(e.getName());
		dto.setStatus(e.getStatus());
		return dto;
	}

}
