package com.hsbc.interviewtwitter.service;


import com.google.common.collect.Maps;
import com.hsbc.interviewtwitter.dao.UserDao;
import com.hsbc.interviewtwitter.domain.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@Slf4j
public class FeedService {

    @Autowired
    private UserDao usersDao;

    EmitterProcessor<Tweet> processor = EmitterProcessor.<Tweet>create();
    Map<String, Flux<Tweet>> liveMessagesSubscribers = Maps.newHashMap();

    public void pushTweetToFeed(Tweet tweet) {
        log.debug("Pushing tweet {} to feed", tweet);
        processor.onNext(tweet);
    }


    public Flux<Tweet> getFeedForUser(String user) {
        liveMessagesSubscribers.putIfAbsent(user, getFilteredFlux(user));
        return liveMessagesSubscribers.get(user);
    }

    private Flux<Tweet> getFilteredFlux(String user) {
        return processor.filter(t -> usersDao.getFollowed(user).contains(t.getAuthorId())).log();
    }

}
