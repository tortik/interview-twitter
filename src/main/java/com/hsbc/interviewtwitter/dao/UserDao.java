package com.hsbc.interviewtwitter.dao;


import com.google.common.collect.*;
import com.hsbc.interviewtwitter.domain.Tweet;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Dao layer.
 * Holds user with set of followed user
 * Example:
 * If A follow B, C then B. C will have A as followed user
 */
@Component
public class UserDao {

    private Multimap<String, String> users = HashMultimap.create();

    public void addFollowed(String user, List<String> followed) {
        followed.stream().forEach(fu -> users.put(fu, user));
    }

    public void unFollow(String user, String unFollow) {
        users.remove(unFollow, user);
    }

    public Set<String> getFollowed(String user) {
        return users.get(user).stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

}
