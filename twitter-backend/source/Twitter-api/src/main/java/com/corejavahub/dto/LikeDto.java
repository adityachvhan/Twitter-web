package com.corejavahub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {

	private Long id;

	private UserDto user;

	private TwitDto twit;
}
