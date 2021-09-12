package com.ewbalasuriya.blogpost.service;

import java.util.List;

import com.ewbalasuriya.blogpost.dto.req.PostRequestDto;
import com.ewbalasuriya.blogpost.dto.resp.CommonResponseDto;
import com.ewbalasuriya.blogpost.entity.BlogPostEntity;

public interface PostService {

	CommonResponseDto createPost(PostRequestDto postRequestDto) throws Exception;

	BlogPostEntity getBlogPostEntity(PostRequestDto dto);

	List<PostRequestDto> getAll() throws Exception;

	PostRequestDto getPostRequestDto(BlogPostEntity e);

	List<PostRequestDto> getAllApproved(String status) throws Exception;

	PostRequestDto get(Integer id) throws Exception;

	List<PostRequestDto> getAllByCategory(Integer id) throws Exception;

	List<PostRequestDto> getByUser(Integer id) throws Exception;

	CommonResponseDto changeApproveStatus(Integer id, Integer adminId, String status) throws Exception;

	CommonResponseDto changeStatus(Integer id, Integer status) throws Exception;

}
