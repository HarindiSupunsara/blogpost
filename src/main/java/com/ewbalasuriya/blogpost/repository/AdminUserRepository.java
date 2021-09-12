package com.ewbalasuriya.blogpost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewbalasuriya.blogpost.entity.AdminUserEntity;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Integer>{

	AdminUserEntity findFirstByUserNameOrEmailOrMobileAndPasswordAndStatus(String username, String email,
			String mobile, String password, Integer status);

	AdminUserEntity findFirstByUserNameAndStatus(String username, Integer active);

	AdminUserEntity findFirstByUserNameOrEmailOrMobileAndStatus(String userName, String email, String mobile,
			Integer active);

	AdminUserEntity findFirstByIdAndPasswordAndStatus(Integer id, String encode, Integer active);

	AdminUserEntity findFirstByUserNameAndPasswordOrEmailAndPasswordOrMobileAndPasswordAndStatus(String username,
			String password, String email, String password1, String mobile, String password2, Integer active);

}
