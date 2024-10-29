package com.corejavahub.service;

import java.util.List;

import com.corejavahub.exception.TwitException;
import com.corejavahub.exception.UserException;
import com.corejavahub.model.Twit;
import com.corejavahub.model.User;
import com.corejavahub.request.TwitReplyRequest;

public interface TwitService {

	public Twit createdTwit(Twit req, User user) throws UserException;

	public List<Twit> findAllTwit();

    public Twit retwit(Long twitId, User user) throws UserException, TwitException;

	public Twit findById(Long twitId) throws TwitException;

	public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException;

	public Twit removeFromRetwit(Long twitId, User user) throws UserException, TwitException;

	public Twit createdReply(TwitReplyRequest req, User user) throws TwitException;

	public List<Twit> getUserTwit(User user);

	public List<Twit> findByLikesContainsUser(User user);
}
