package com.corejavahub.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	private String fullName;
	private String email;
	private String image;
	private String location;
	private String website;
	private String birthDate;
	private String mobile;
	private String backgroundImage;
	private String bio;
	private boolean req_user;

	private boolean login_with_google;

	private List<UserDto> followers = new ArrayList<>();
	private List<UserDto> following = new ArrayList<>();

	private boolean followed;
	
	private boolean isVarified;
}
