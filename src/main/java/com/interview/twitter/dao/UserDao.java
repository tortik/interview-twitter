package com.interview.twitter.dao;


import com.google.common.collect.*;
import com.interview.twitter.domain.FollowUser;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Dao layer.
 * Holds user with set of followed user
 * Example:
 * If A follow B, then B will have A as followed user
 */
@Component
public class UserDao {

    private Multimap<String, FollowUser> users =  Multimaps.synchronizedMultimap(HashMultimap.create());

    public void addFollowers(String user, List<String> followed) {
        followed.stream().forEach(fu -> users.put(fu, FollowUser.of(user)));
    }

    public void unFollow(String user, String unFollow) {
        Optional<FollowUser> userToRemove = users.get(unFollow).stream().filter(u -> u.getUser().equals(user)).findFirst();
        userToRemove.ifPresent(fu -> users.remove(unFollow, fu));
    }

    public Set<FollowUser> getFollowed(String user) {
        return users.get(user).stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

}
