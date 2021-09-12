package com.ewbalasuriya.blogpost.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ewbalasuriya.blogpost.configuration.EwbPasswordEncoder;
import com.ewbalasuriya.blogpost.dto.req.AuthRequestDto;
import com.ewbalasuriya.blogpost.dto.req.PasswordChangeRequestDto;
import com.ewbalasuriya.blogpost.dto.req.RegisterRequestDto;
import com.ewbalasuriya.blogpost.dto.resp.CommonResponseDto;
import com.ewbalasuriya.blogpost.entity.AdminUserEntity;
import com.ewbalasuriya.blogpost.entity.UserEntity;
import com.ewbalasuriya.blogpost.repository.AdminUserRepository;
import com.ewbalasuriya.blogpost.repository.UserRepository;
import com.ewbalasuriya.blogpost.service.UserService;
import com.ewbalasuriya.blogpost.util.AppConstant;
import com.ewbalasuriya.blogpost.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdminUserRepository adminUserRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findFirstByUserNameAndStatus(username, AppConstant.ACTIVE);
		if (userEntity == null) {
			AdminUserEntity adminUserEntity = adminUserRepository.findFirstByUserNameAndStatus(username,
					AppConstant.ACTIVE);
			if (adminUserEntity == null) {
				return null;
			}
		}
		return new org.springframework.security.core.userdetails.User(username, "", new ArrayList<>());
	}

	@Override
	public CommonResponseDto login(AuthRequestDto authRequestDto) throws Exception {
		CommonResponseDto commonResponseDto = new CommonResponseDto();
		UserEntity userEntity = userRepository.findFirstByUserNameAndPasswordOrEmailAndPasswordOrMobileAndPasswordAndStatus(
				authRequestDto.getUsername(),new EwbPasswordEncoder().encode(authRequestDto.getPassword()), 
				authRequestDto.getUsername(),new EwbPasswordEncoder().encode(authRequestDto.getPassword()),
				authRequestDto.getUsername(), new EwbPasswordEncoder().encode(authRequestDto.getPassword()), AppConstant.ACTIVE);

		if (userEntity != null) {
			String accessToken = createToken(userEntity.getUserName());
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id", userEntity.getId());
			data.put("token", accessToken);
			data.put("admin", false);
			data.put("name", userEntity.getFirstName() + " " + userEntity.getLastName());
			commonResponseDto.setMessage("Success");
			commonResponseDto.setStatus("200");
			commonResponseDto.setData(data);

		} else {

			AdminUserEntity adminUserEntity = adminUserRepository
					.findFirstByUserNameAndPasswordOrEmailAndPasswordOrMobileAndPasswordAndStatus(
							authRequestDto.getUsername(),new EwbPasswordEncoder().encode(authRequestDto.getPassword()), 
							authRequestDto.getUsername(),new EwbPasswordEncoder().encode(authRequestDto.getPassword()),
							authRequestDto.getUsername(), new EwbPasswordEncoder().encode(authRequestDto.getPassword()), AppConstant.ACTIVE);

			if (adminUserEntity != null) {
				String accessToken = createToken(adminUserEntity.getUserName());
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("id", adminUserEntity.getId());
				data.put("token", accessToken);
				data.put("admin", true);
				data.put("isSuperAdmin", adminUserEntity.getSuperadmin());
				data.put("name", adminUserEntity.getFirstName() + " " + adminUserEntity.getLastName());
				commonResponseDto.setMessage("Success");
				commonResponseDto.setStatus("200");
				commonResponseDto.setData(data);

			} else {
				commonResponseDto.setMessage("Fail");
				commonResponseDto.setStatus("404");
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("message", "USER NOT FOUND");
			}
		}

		return commonResponseDto;
	}

	@Override
	public String createToken(String username) {
		String accessToken = jwtUtil.generateToken(username, "");
		return accessToken;
	}

	@Override
	public CommonResponseDto register(RegisterRequestDto registerRequestDto) throws Exception {
		CommonResponseDto commonResponseDto = new CommonResponseDto();

		UserEntity entity = userRepository.findFirstByUserNameOrEmailOrMobileAndStatus(registerRequestDto.getUserName(),
				registerRequestDto.getEmail(), registerRequestDto.getMobile(), AppConstant.ACTIVE);
		AdminUserEntity adminUserentity = adminUserRepository.findFirstByUserNameOrEmailOrMobileAndStatus(
				registerRequestDto.getUserName(), registerRequestDto.getEmail(), registerRequestDto.getMobile(),
				AppConstant.ACTIVE);
		if (entity != null && adminUserentity != null) {
			commonResponseDto.setMessage("Already Exist");
			commonResponseDto.setStatus("409");
		} else {
			entity = getUserEntity(registerRequestDto);
			if (userRepository.save(entity) != null) {
				commonResponseDto.setMessage("Success");
				commonResponseDto.setStatus("200");
			} else {
				commonResponseDto.setMessage("Fail");
				commonResponseDto.setStatus("500");
			}
		}

		return commonResponseDto;
	}

	@Override
	public UserEntity getUserEntity(RegisterRequestDto registerRequestDto) {
		UserEntity entity = new UserEntity();
		entity.setEmail(registerRequestDto.getEmail());
		entity.setFirstName(registerRequestDto.getFirstName());
		entity.setLastName(registerRequestDto.getLastName());
		entity.setMobile(registerRequestDto.getMobile());
		entity.setPassword(new EwbPasswordEncoder().encode(registerRequestDto.getPassword()));
		entity.setStatus(AppConstant.ACTIVE);
		entity.setUserName(registerRequestDto.getUserName());
		entity.setCreateDate(new Date());
		return entity;
	}

	@Override
	public CommonResponseDto adminRegister(RegisterRequestDto registerRequestDto) throws Exception {
		CommonResponseDto commonResponseDto = new CommonResponseDto();

		AdminUserEntity entity = adminUserRepository.findFirstByUserNameOrEmailOrMobileAndStatus(
				registerRequestDto.getUserName(), registerRequestDto.getEmail(), registerRequestDto.getMobile(),
				AppConstant.ACTIVE);
		UserEntity userEntity = userRepository.findFirstByUserNameOrEmailOrMobileAndStatus(
				registerRequestDto.getUserName(), registerRequestDto.getEmail(), registerRequestDto.getMobile(),
				AppConstant.ACTIVE);
		if (entity != null && userEntity != null && registerRequestDto.getId() == null) {
			commonResponseDto.setMessage("Already Exist");
			commonResponseDto.setStatus("409");
		} else {
			entity = getAdminUserEntity(registerRequestDto);
			if (adminUserRepository.save(entity) != null) {
				commonResponseDto.setMessage("Success");
				commonResponseDto.setStatus("200");
			} else {
				commonResponseDto.setMessage("Fail");
				commonResponseDto.setStatus("500");
			}
		}

		return commonResponseDto;
	}

	@Override
	public AdminUserEntity getAdminUserEntity(RegisterRequestDto registerRequestDto) {
		
		AdminUserEntity entity = null;
		if(registerRequestDto.getId() != null) {
			entity = adminUserRepository.findById(registerRequestDto.getId()).orElse(null);
		}
		
		if(entity == null) {
			entity = new AdminUserEntity();
			entity.setCreateDate(new Date());
		}
		
		entity.setEmail(registerRequestDto.getEmail());
		entity.setFirstName(registerRequestDto.getFirstName());
		entity.setLastName(registerRequestDto.getLastName());
		entity.setMobile(registerRequestDto.getMobile());
		entity.setPassword(new EwbPasswordEncoder().encode(registerRequestDto.getPassword()));
		entity.setStatus(AppConstant.ACTIVE);
		entity.setUserName(registerRequestDto.getUserName());
		
		entity.setSuperadmin(registerRequestDto.isSuperAdmin());
		return entity;
	}

	@Override
	public CommonResponseDto changePassword(PasswordChangeRequestDto passwordChangeRequestDto) throws Exception {
		CommonResponseDto commonResponseDto = new CommonResponseDto();
		if (passwordChangeRequestDto.getType().equals(AppConstant.ADMIN)) {
			AdminUserEntity entity = adminUserRepository.findFirstByIdAndPasswordAndStatus(
					passwordChangeRequestDto.getId(),
					new EwbPasswordEncoder().encode(passwordChangeRequestDto.getCurrentPassword()), AppConstant.ACTIVE);
			if (entity != null) {
				entity.setPassword(new EwbPasswordEncoder().encode(passwordChangeRequestDto.getNewPassword()));
				if(adminUserRepository.save(entity) != null) {
					commonResponseDto.setMessage("Success");
					commonResponseDto.setStatus("200");
				} else {
					commonResponseDto.setMessage("Fail");
					commonResponseDto.setStatus("500");
				}
			} else {
				commonResponseDto.setMessage("Not Found");
				commonResponseDto.setStatus("404");
			}
		}
		if (passwordChangeRequestDto.getType().equals(AppConstant.USER)) {
			UserEntity entity = userRepository.findFirstByIdAndPasswordAndStatus(passwordChangeRequestDto.getId(),
					new EwbPasswordEncoder().encode(passwordChangeRequestDto.getCurrentPassword()), AppConstant.ACTIVE);
			if (entity != null) {
				entity.setPassword(new EwbPasswordEncoder().encode(passwordChangeRequestDto.getNewPassword()));
				if(userRepository.save(entity) != null) {
					commonResponseDto.setMessage("Success");
					commonResponseDto.setStatus("200");
				} else {
					commonResponseDto.setMessage("Fail");
					commonResponseDto.setStatus("500");
				}
			} else {
				commonResponseDto.setMessage("Not Found");
				commonResponseDto.setStatus("404");
			}
		}

		return commonResponseDto;
	}

	@Override
	public List<RegisterRequestDto> getAllAdmins() throws Exception {
		List<AdminUserEntity> adminUserEntities = adminUserRepository.findAll();
		List<RegisterRequestDto> dtos = new ArrayList<RegisterRequestDto>();
		
		adminUserEntities.forEach(e-> {
			dtos.add(getRegisterRequestDto(e));
		});
		
		return dtos;
	}

	@Override
	public RegisterRequestDto getRegisterRequestDto(AdminUserEntity e) {
		RegisterRequestDto dto = new RegisterRequestDto();
		dto.setEmail(e.getEmail());
		dto.setFirstName(e.getFirstName());
		dto.setId(e.getId());
		dto.setLastName(e.getLastName());
		dto.setMobile(e.getMobile());
		//dto.setPassword(e.getPassword());
		dto.setStatus(e.getStatus());
		dto.setSuperAdmin(e.getSuperadmin());
		dto.setUserName(e.getUserName());
		return dto;
	}

	@Override
	public RegisterRequestDto getAdmin(Integer id) throws Exception {
		AdminUserEntity entity = adminUserRepository.findById(id).orElse(null);
		if(entity != null) {
			return getRegisterRequestDto(entity);
		}
		return null;
	}

	@Override
	public CommonResponseDto changeStatus(Integer id, Integer status) throws Exception {
		CommonResponseDto commonResponseDto = new CommonResponseDto();
		commonResponseDto.setStatus("500");
		commonResponseDto.setMessage("Fail");
		
		System.out.println(status);
		
		AdminUserEntity entity = adminUserRepository.findById(id).orElse(null);
		if(entity != null) {
			entity.setStatus(status);
			adminUserRepository.save(entity);
			commonResponseDto.setStatus("200");
			commonResponseDto.setMessage("Success");
		}
		
		return commonResponseDto;
	}

	@Override
	public CommonResponseDto getResultCount() throws Exception {
		CommonResponseDto dto = new CommonResponseDto();
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		Long count = 0L;
		count = adminUserRepository.count();
		dto.setStatus("200");
		dto.setStatus("Success");
		data.put("Count", count);
		dto.setData(data);
		
		return dto;
	}

}
