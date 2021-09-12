package com.ewbalasuriya.blogpost.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ewbalasuriya.blogpost.dto.req.AuthRequestDto;
import com.ewbalasuriya.blogpost.dto.req.PasswordChangeRequestDto;
import com.ewbalasuriya.blogpost.dto.req.RegisterRequestDto;
import com.ewbalasuriya.blogpost.dto.resp.CommonResponseDto;
import com.ewbalasuriya.blogpost.entity.AdminUserEntity;
import com.ewbalasuriya.blogpost.entity.UserEntity;

public interface UserService extends UserDetailsService {

	CommonResponseDto login(AuthRequestDto authRequestDto) throws Exception;

	CommonResponseDto register(RegisterRequestDto registerRequestDto) throws Exception;

	UserEntity getUserEntity(RegisterRequestDto registerRequestDto);

	String createToken(String username);

	CommonResponseDto adminRegister(RegisterRequestDto registerRequestDto) throws Exception;

	AdminUserEntity getAdminUserEntity(RegisterRequestDto registerRequestDto);

	CommonResponseDto changePassword(PasswordChangeRequestDto passwordChangeRequestDto) throws Exception;

	List<RegisterRequestDto> getAllAdmins() throws Exception;

	RegisterRequestDto getRegisterRequestDto(AdminUserEntity e);

	RegisterRequestDto getAdmin(Integer id) throws Exception;

	CommonResponseDto changeStatus(Integer id, Integer status) throws Exception;

	CommonResponseDto getResultCount() throws Exception;

}
