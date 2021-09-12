package com.ewbalasuriya.blogpost.dto.req;

import java.util.Date;

import lombok.Data;

@Data
public class PostRequestDto {

	private Integer id;
	private String header;
	private String description;
	private String postStatus;
	private Integer status;
	private Date createDate;
	private Date approveDate;
	private Integer adminId;
	private Integer userId;
	private Integer categoryId;
	private String userName;
	private String categoryName;
	private String approveAdminName;
}
