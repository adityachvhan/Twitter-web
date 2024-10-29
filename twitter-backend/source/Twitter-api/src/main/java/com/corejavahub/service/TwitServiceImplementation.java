package com.corejavahub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corejavahub.exception.TwitException;
import com.corejavahub.exception.UserException;
import com.corejavahub.model.Like;
import com.corejavahub.model.Twit;
import com.corejavahub.model.User;
import com.corejavahub.repository.TwitRepository;
import com.corejavahub.request.TwitReplyRequest;

@Service
public class TwitServiceImplementation implements TwitService {

	@Autowired
	private TwitRepository twitRepository;

	@Override
	public Twit createdTwit(Twit req, User user) throws UserException {

		Twit twit = new Twit();
		twit.setContent(req.getContent());
		twit.setCreatedAt(LocalDateTime.now());
		twit.setImage(req.getImage());
		twit.setUser(user);
		twit.setReply(false);
		twit.setTwit(true);
		twit.setVideo(req.getVideo());

		return twitRepository.save(twit);
	}

	@Override
	public List<Twit> findAllTwit() {

		return twitRepository.findAllByIsTwitTrueOrderByCreatedAtDesc();
	}

	@Override
	public Twit retwit(Long twitId, User user) throws UserException, TwitException {
		Twit twit = findById(twitId);

		// Toggle the retweet status for the user
		if (twit.getRetwitUser().contains(user)) {
			twit.getRetwitUser().remove(user);
		} else {
			twit.getRetwitUser().add(user);
		}
		return twitRepository.save(twit);
	}

	@Override
	public Twit findById(Long twitId) throws TwitException {

		Twit twit = twitRepository.findById(twitId)
				.orElseThrow(() -> new TwitException("Twit with the specified ID does not exist."));
		return twit;
	}

	@Override
	public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {

		Twit twit = findById(twitId);

		// Check if the user is the owner of the twit
		if (!userId.equals(twit.getUser().getId())) {
			throw new UserException("User is not authorized to delete this twit.");
		}
		twitRepository.deleteById(twit.getId());
	}

	@Override
	public Twit removeFromRetwit(Long twitId, User user) throws UserException, TwitException {

		return null;
	}

	@Override
	public Twit createdReply(TwitReplyRequest req, User user) throws TwitException {

		Twit replyFor = findById(req.getTwitId());

		Twit twit = new Twit();
		twit.setContent(req.getContent());
		twit.setCreatedAt(LocalDateTime.now());
		twit.setImage(req.getImage());
		twit.setUser(user);
		twit.setReply(true); // This is a reply, not an original twit
		twit.setTwit(false); // Indicating this is a reply, not an independent twit
		twit.setReplyFor(replyFor); // Link this reply to the original twit

		// Save the reply twit
		Twit savedReply = twitRepository.save(twit);

		// Add the reply to the original twit's list of replies
//		twit.getReplyTwits().add(savedReply);

		replyFor.getReplyTwits().add(savedReply);

		// Save the original twit with the updated list of replies
		twitRepository.save(replyFor);

		return replyFor; // Return the newly created reply twit
	}

	@Override
	public List<Twit> getUserTwit(User user) {
		// TODO Auto-generated method stub
		return twitRepository.findByRetwitUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAtDesc(user, user.getId());
	}

	@Override
	public List<Twit> findByLikesContainsUser(User user) {
		// TODO Auto-generated method stub
		return twitRepository.findByLikesUser_Id(user.getId());
	}

}
