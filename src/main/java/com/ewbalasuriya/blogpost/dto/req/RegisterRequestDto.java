package com.ewbalasuriya.blogpost.dto.req;

import lombok.Data;

@Data
public class RegisterRequestDto {

	private Integer id;
	private String userName;
	private String password;
	private Integer status;
	private String email;
	private String firstName;
	private String lastName;
	private String mobile;
	private boolean superAdmin;
}
