package com.corejavahub.service;

import java.util.List;

import com.corejavahub.exception.TwitException;
import com.corejavahub.exception.UserException;
import com.corejavahub.model.Like;
import com.corejavahub.model.User;

public interface LikeService {

	public Like likeTwit(Long twitId, User user) throws UserException, TwitException;

	public List<Like> getAllLikes(Long twitId) throws TwitException;
}
