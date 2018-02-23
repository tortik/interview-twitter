package com.hsbc.interviewtwitter.dao;


import com.google.common.collect.Maps;
import com.hsbc.interviewtwitter.domain.Tweet;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class TweetsDao {

    private Map<String, Tweet> tweets = Maps.newHashMap();

    public void storeTweet(Tweet tweet) {
        tweets.put(tweet.getId(), tweet);
    }




}
