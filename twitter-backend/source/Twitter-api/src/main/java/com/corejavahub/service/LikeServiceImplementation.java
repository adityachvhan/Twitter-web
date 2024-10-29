package com.corejavahub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corejavahub.exception.TwitException;
import com.corejavahub.exception.UserException;
import com.corejavahub.model.Like;
import com.corejavahub.model.Twit;
import com.corejavahub.model.User;
import com.corejavahub.repository.LikeRepository;
import com.corejavahub.repository.TwitRepository;

@Service
public class LikeServiceImplementation implements LikeService {

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private TwitService twitService;

	@Autowired
	private TwitRepository twitRepository;

	@Override
	public Like likeTwit(Long twitId, User user) throws UserException, TwitException {

		Like isLikeExist = likeRepository.isLikeExist(twitId, user.getId());

		if (isLikeExist != null) {

			likeRepository.deleteById(isLikeExist.getId());

			return isLikeExist;

		}

		Twit twit = twitService.findById(twitId);

		Like like = new Like();
		like.setTwit(twit);
		like.setUser(user);

		Like savedLike = likeRepository.save(like);

		twit.getLikes().add(savedLike);
		twitRepository.save(twit);

		return savedLike;
	}

	@Override
	public List<Like> getAllLikes(Long twitId) throws TwitException {

		Twit twit = twitService.findById(twitId);

		List<Like> likes = likeRepository.findByTwitId(twitId);

		return likes;
	}

}
