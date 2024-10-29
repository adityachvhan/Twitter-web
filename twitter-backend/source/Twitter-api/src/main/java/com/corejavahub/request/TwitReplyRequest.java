package com.corejavahub.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class TwitReplyRequest {

	private String content;
	private Long twitId;
	private LocalDateTime createdAt;
	private String image;
}
