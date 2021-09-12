package com.ewbalasuriya.blogpost.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewbalasuriya.blogpost.dto.req.PostRequestDto;
import com.ewbalasuriya.blogpost.dto.resp.CommonResponseDto;
import com.ewbalasuriya.blogpost.entity.AdminUserEntity;
import com.ewbalasuriya.blogpost.entity.BlogPostEntity;
import com.ewbalasuriya.blogpost.entity.CategoryEntity;
import com.ewbalasuriya.blogpost.entity.UserEntity;
import com.ewbalasuriya.blogpost.repository.AdminUserRepository;
import com.ewbalasuriya.blogpost.repository.BlogPostRepository;
import com.ewbalasuriya.blogpost.repository.CategoryRepository;
import com.ewbalasuriya.blogpost.repository.UserRepository;
import com.ewbalasuriya.blogpost.service.PostService;
import com.ewbalasuriya.blogpost.util.AppConstant;


@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private BlogPostRepository postRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminUserRepository adminUserRepository;

	@Override
	public CommonResponseDto createPost(PostRequestDto postRequestDto) throws Exception {
		CommonResponseDto commonResponseDto = new CommonResponseDto();

		BlogPostEntity entity = getBlogPostEntity(postRequestDto);
		if (postRepository.save(entity) != null) {
			commonResponseDto.setMessage("Success");
			commonResponseDto.setStatus("200");
		} else {
			commonResponseDto.setMessage("Fail");
			commonResponseDto.setStatus("500");
		}

		return commonResponseDto;
	}

	@Override
	public BlogPostEntity getBlogPostEntity(PostRequestDto dto) {
		BlogPostEntity entity = null;
		if(dto.getId() != null) {
			entity = postRepository.findById(dto.getId()).orElse(null);
		}
		
		if(entity == null) {
			entity = new BlogPostEntity();
			entity.setCreateDate(new Date());
		}
		
		CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId()).get();
		UserEntity userEntity = userRepository.findById(dto.getUserId()).get();
		entity.setCategoryEntity(categoryEntity);
		entity.setDescription(dto.getDescription());
		entity.setHeader(dto.getHeader());
		entity.setPostStatus("INACTIVE");
		entity.setStatus(AppConstant.ACTIVE);
		entity.setUserEntity(userEntity);
		
		return entity;
	}

	@Override
	public List<PostRequestDto> getAll() throws Exception {
		List<PostRequestDto> dtos = new ArrayList<PostRequestDto>();
		List<BlogPostEntity> blogPostEntities = postRepository.findAll();
		
		blogPostEntities.forEach(e-> {
			dtos.add(getPostRequestDto(e));
		});
		
		
		return dtos;
	}

	@Override
	public PostRequestDto getPostRequestDto(BlogPostEntity e) {
		PostRequestDto dto = new PostRequestDto();
		dto.setAdminId(e.getAdminUserEntity() != null ? e.getAdminUserEntity().getId() : null);
		dto.setApproveAdminName(e.getAdminUserEntity() != null ? e.getAdminUserEntity().getFirstName() + " " +e.getAdminUserEntity().getLastName() : null);
		dto.setApproveDate(e.getApproveDate());
		dto.setCategoryId(e.getCategoryEntity() != null ? e.getCategoryEntity().getId() : null);
		dto.setCategoryName(e.getCategoryEntity() != null ? e.getCategoryEntity().getName() : null);
		dto.setCreateDate(e.getCreateDate());
		dto.setDescription(e.getDescription());
		dto.setHeader(e.getHeader());
		dto.setId(e.getId());
		dto.setPostStatus(e.getPostStatus());
		dto.setStatus(e.getStatus());
		dto.setUserId(e.getUserEntity() != null ? e.getUserEntity().getId() : null);
		dto.setUserName(e.getUserEntity() != null ? e.getUserEntity().getFirstName() + " " + e.getUserEntity().getLastName() : null);
		
		return dto;
	}

	@Override
	public List<PostRequestDto> getAllApproved(String status) throws Exception {
		List<PostRequestDto> dtos = new ArrayList<PostRequestDto>();
		List<BlogPostEntity> blogPostEntities = postRepository.findByStatusAndPostStatus(AppConstant.ACTIVE, status);
		
		blogPostEntities.forEach(e-> {
			dtos.add(getPostRequestDto(e));
		});
		
		return dtos;
	}

	@Override
	public PostRequestDto get(Integer id) throws Exception {
		BlogPostEntity blogPostEntity = postRepository.findById(id).get();
		return getPostRequestDto(blogPostEntity);
	}

	@Override
	public List<PostRequestDto> getAllByCategory(Integer id) throws Exception {
		List<PostRequestDto> postRequestDtos = new ArrayList<PostRequestDto>();
		CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
		if(categoryEntity != null) {
			List<BlogPostEntity> blogPostEntities = postRepository.findByCategoryEntityAndStatusAndPostStatus(categoryEntity, AppConstant.ACTIVE, "ACTIVE");
			blogPostEntities.forEach(e-> {
				postRequestDtos.add(getPostRequestDto(e));
			});
			
		}
		return postRequestDtos;
	}

	@Override
	public List<PostRequestDto> getByUser(Integer id) throws Exception {
		List<PostRequestDto> postRequestDtos = new ArrayList<PostRequestDto>();
		UserEntity userEntity = userRepository.findById(id).orElse(null);
		if(userEntity != null) {
			List<BlogPostEntity> blogPostEntities = postRepository.findByUserEntityAndStatus(userEntity, AppConstant.ACTIVE);
			blogPostEntities.forEach(e-> {
				postRequestDtos.add(getPostRequestDto(e));
			});
			
		}
		return postRequestDtos;
	}

	@Override
	public CommonResponseDto changeApproveStatus(Integer id, Integer adminId, String status) throws Exception {
		CommonResponseDto commonResponseDto = new CommonResponseDto();
		commonResponseDto.setStatus("500");
		commonResponseDto.setMessage("Fail");
		
		System.out.println(status);
		
		AdminUserEntity entity = adminUserRepository.findById(adminId).get();
		BlogPostEntity postEntity = postRepository.findById(id).get();
		
		if(postEntity != null && entity != null) {
			postEntity.setAdminUserEntity(entity);
			postEntity.setApproveDate(new Date());
			postEntity.setPostStatus(status);
			postRepository.save(postEntity);
			commonResponseDto.setStatus("200");
			commonResponseDto.setMessage("Success");
		}
		
		return commonResponseDto;
	}

	@Override
	public CommonResponseDto changeStatus(Integer id, Integer status) throws Exception {
		CommonResponseDto commonResponseDto = new CommonResponseDto();
		commonResponseDto.setStatus("500");
		commonResponseDto.setMessage("Fail");
		
		System.out.println(status);
		
		BlogPostEntity entity = postRepository.findById(id).get();
		if(entity != null) {
			entity.setStatus(status);
			postRepository.save(entity);
			commonResponseDto.setStatus("200");
			commonResponseDto.setMessage("Success");
		}
		
		return commonResponseDto;
	}

}
