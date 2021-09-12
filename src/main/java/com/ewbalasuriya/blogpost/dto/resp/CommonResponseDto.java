package com.ewbalasuriya.blogpost.dto.resp;

import java.util.HashMap;


import lombok.Data;

@Data
public class CommonResponseDto {

	private String status;
	private String message;
	private HashMap<String, Object> data;
}
