package com.hsbc.interviewtwitter.service;


import com.hsbc.interviewtwitter.dao.TweetsDao;
import com.hsbc.interviewtwitter.dao.UserDao;
import com.hsbc.interviewtwitter.domain.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.UUID;

@Slf4j
@Service

public class TweetsService {

    @Autowired
    private TweetsDao tweetsDao;
    @Autowired
    private UserDao usersDao;
    @Autowired
    private FeedService feedService;

    public String createTweet(String currentUser, String tweetMessage) {

        Tweet newTweet = new Tweet(generateId(), currentUser, tweetMessage);

        log.trace("Created new tweet {}", newTweet);
        tweetsDao.storeTweet(newTweet);
        log.debug("Stored tweet {}", newTweet);

        feedService.pushTweetToFeed(newTweet);
        return newTweet.getId();
    }


    public String generateId() {
        return UUID.randomUUID().toString();
    }

}
