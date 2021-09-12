package com.ewbalasuriya.blogpost.service;

import java.util.List;

import com.ewbalasuriya.blogpost.dto.resp.CategoryResponceDto;
import com.ewbalasuriya.blogpost.entity.CategoryEntity;

public interface CategoryService {

	List<CategoryResponceDto> getAllActive() throws Exception;

	CategoryResponceDto getCategoryResponceDto(CategoryEntity e);

}
