package com.hsbc.interviewtwitter.dao;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.hsbc.interviewtwitter.domain.Tweet;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class TweetsDao {

    private Map<String, Tweet> globalTweetStorage = Maps.newHashMap();
    private Multimap<String, Tweet> myTweets = HashMultimap.create();
    private Multimap<String, Tweet> feedTweets = HashMultimap.create();

    public void storeTweet(String user, Tweet tweet) {
        myTweets.put(user, tweet);
        globalTweetStorage.put(tweet.getTweetId(), tweet);
    }

    public void removeTweet(String user, Tweet tweet) {
        myTweets.get(user).remove(tweet);
        globalTweetStorage.remove(tweet.getTweetId());
        //remove from all feeds
        feedTweets.keySet().stream().forEach(
                u -> feedTweets.remove(user, tweet)
        );
    }

    public void storeFeedTweets(String user, Tweet tweet) {
        feedTweets.put(user, tweet);
    }

    public Tweet getTweet(String tweetId) {
        return globalTweetStorage.get(tweetId);
    }

    public Collection<Tweet> getMyTweets(String user) {
        return myTweets.get(user);
    }
    public Collection<Tweet> getFeedTweets(String user) {
        return feedTweets.get(user);
    }


}
