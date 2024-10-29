package com.corejavahub.util;

import java.time.LocalDateTime;

import com.corejavahub.model.User;

public class UserUtil {

	public final static boolean isReqUser(User reqUser, User user2) {

		return reqUser.getId().equals(user2.getId());
	}

	public final static boolean isFollowedByReqUser(User reqUser, User user2) {

		return reqUser.getFollowings().contains(user2);
	}

	public static final boolean isVerified(LocalDateTime endsDate) {
		if (endsDate != null)
			return endsDate.isAfter(LocalDateTime.now());
		else
			return false;
	}

}
