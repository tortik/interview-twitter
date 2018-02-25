package com.interview.twitter.service;


import com.interview.twitter.common.exception.ResourceNotFoundException;
import com.interview.twitter.dao.TweetsDao;
import com.interview.twitter.domain.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@Service

public class TweetsService {

    @Autowired
    private TweetsDao tweetsDao;
    @Autowired
    private FeedService feedService;

    public Tweet createTweet(String currentUser, String tweetMessage) {

        Tweet newTweet = new Tweet(generateId(), currentUser, tweetMessage);
        log.trace("Created new tweet {}", newTweet);

        return saveTweet(currentUser, newTweet);
    }

    public Tweet updateTweet(String currentUser, String tweetId, String updatedMessage) {

        Tweet tweet = getTweet(currentUser, tweetId);

        tweet.setText(updatedMessage);
        tweet.setUpdatedDate(ZonedDateTime.now());

        return saveTweet(currentUser, tweet);
    }

    public void removeTweet(String currentUser, String tweetId) {

        Tweet tweet = getTweet(currentUser, tweetId);

        tweetsDao.removeTweet(currentUser, tweet);
    }

    public List<Tweet> getMyTweets(String currentUser) {
        return new ArrayList<>(tweetsDao.getMyTweets(currentUser));
    }

    private Tweet getTweet(String currentUser, String tweetId) {
        return Optional.ofNullable(tweetsDao.getTweet(tweetId)).
                filter(t -> currentUser.equals(t.getAuthorId())).
                orElseThrow(() -> new ResourceNotFoundException(String.format("Can't find tweet with id=%s", tweetId)));
    }

    private Tweet saveTweet(String currentUser, Tweet tweet) {

        tweetsDao.storeTweet(currentUser, tweet);
        log.debug("Stored tweet {}", tweet);

        feedService.storeAsyncFeedTweet(tweet);
        return tweet;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
