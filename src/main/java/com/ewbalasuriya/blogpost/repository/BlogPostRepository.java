package com.ewbalasuriya.blogpost.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewbalasuriya.blogpost.entity.BlogPostEntity;
import com.ewbalasuriya.blogpost.entity.CategoryEntity;
import com.ewbalasuriya.blogpost.entity.UserEntity;

public interface BlogPostRepository extends JpaRepository<BlogPostEntity, Integer>{

	List<BlogPostEntity> findByStatusAndPostStatus(Integer active, String string);

	List<BlogPostEntity> findByCategoryEntityAndStatus(CategoryEntity categoryEntity, Integer active);

	List<BlogPostEntity> findByUserEntityAndStatus(UserEntity userEntity, Integer active);

	List<BlogPostEntity> findByCategoryEntityAndStatusAndPostStatus(CategoryEntity categoryEntity, Integer active,
			String string);

}
