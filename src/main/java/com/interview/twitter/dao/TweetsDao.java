package com.interview.twitter.dao;


import com.google.common.collect.*;
import com.interview.twitter.domain.Tweet;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Component
public class TweetsDao {

    private Map<String, Tweet> globalTweetStorage = new ConcurrentHashMap<>();
    private Multimap<String, Tweet> myTweets;
    private Multimap<String, Tweet> feedTweets;

    public TweetsDao() {
        Supplier<SortedSetMultimap<String, Tweet>> sortedMultiSet = () -> MultimapBuilder.SortedSetMultimapBuilder.<String>
                hashKeys().treeSetValues(Comparator.comparing(Tweet::getUpdatedDate).reversed()).build();

        feedTweets = Multimaps.synchronizedMultimap(sortedMultiSet.get());
        myTweets = Multimaps.synchronizedMultimap(sortedMultiSet.get());
    }

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
