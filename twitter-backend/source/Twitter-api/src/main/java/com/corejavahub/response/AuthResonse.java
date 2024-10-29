package com.corejavahub.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class AuthResonse {

	private String jwt;
	private boolean status;
}
