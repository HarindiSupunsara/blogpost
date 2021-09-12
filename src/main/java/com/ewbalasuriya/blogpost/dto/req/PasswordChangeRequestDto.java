package com.ewbalasuriya.blogpost.dto.req;

import lombok.Data;

@Data
public class PasswordChangeRequestDto {

	private Integer id;
	private String type;
	private String currentPassword;
	private String newPassword;
}
