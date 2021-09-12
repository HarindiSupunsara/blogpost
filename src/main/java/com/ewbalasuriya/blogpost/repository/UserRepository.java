package com.ewbalasuriya.blogpost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewbalasuriya.blogpost.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	UserEntity findFirstByUserNameAndStatus(String username, Integer status) ;

	UserEntity findFirstByUserNameOrEmailOrMobileAndStatus(String username, String email,
			String mobile, Integer status);

	UserEntity findFirstByUserNameOrEmailOrMobileAndPasswordAndStatus(String username, String email,
			String mobile, String assword, Integer status);

	UserEntity findFirstByUserNameAndPasswordAndStatus(String username,
			String encode, Integer active);

	UserEntity findFirstByIdAndPasswordAndStatus(Integer id, String encode, Integer active);

	UserEntity findFirstByUserNameAndPasswordOrEmailAndPasswordOrMobileAndPasswordAndStatus(String username,
			String password, String email, String password1, String mobile, String password2, Integer active);

}
