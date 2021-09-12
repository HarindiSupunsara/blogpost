package com.ewbalasuriya.blogpost.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewbalasuriya.blogpost.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

	List<CategoryEntity> findByStatus(Integer active);

}
