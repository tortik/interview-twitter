package com.hsbc.interviewtwitter.dao;


import com.google.common.collect.*;
import com.hsbc.interviewtwitter.domain.Tweet;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserDao {

    private Multimap<String, String> users = HashMultimap.create();

    public void addFollowed(String user, List<String> followed) {
        users.putAll(user, followed);
    }

    public Set<String> getFollowed(String user) {
        return users.get(user).stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public void createUser(String user) {
        users.putAll(user, Collections.emptySet());
    }


}
