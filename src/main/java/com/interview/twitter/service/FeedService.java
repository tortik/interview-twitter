package com.interview.twitter.service;


import com.google.common.collect.Maps;
import com.interview.twitter.dao.TweetsDao;
import com.interview.twitter.dao.UserDao;
import com.interview.twitter.domain.FollowUser;
import com.interview.twitter.domain.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class FeedService {

    @Autowired
    private UserDao usersDao;
    @Autowired
    private TweetsDao tweetsDao;

    private EmitterProcessor<Tweet> processor = EmitterProcessor.<Tweet>create();
    private Map<String, Flux<Tweet>> liveMessagesSubscribers = Maps.newHashMap();

    public void pushTweetToLive(Tweet tweet) {
        log.debug("Pushing tweet {} to live feed", tweet);
        processor.onNext(tweet);
    }

    public void storeAsyncFeedTweet(Tweet tweet) {
        CompletableFuture.runAsync(() -> {
            log.debug("Storing tweet {} to followers feed", tweet);
            usersDao.getFollowed(tweet.getAuthorId()).stream().
                    map(FollowUser::getUser).
                    peek(u -> log.debug("Saving tweet {} to user feed {}", tweet.getTweetId(), u)).
                    forEach(u -> tweetsDao.storeFeedTweets(u, tweet));

            pushTweetToLive(tweet);
        }).thenAcceptAsync(i -> log.debug("Successfully stored to followers feed tweet with id {}", tweet.getTweetId()));
    }

    public List<Tweet> getFeedTweets(String currentUser) {

        return new ArrayList<>(tweetsDao.getFeedTweets(currentUser));
    }

    public Flux<Tweet> subscribeOnLiveMessages(String user) {
        liveMessagesSubscribers.putIfAbsent(user, getFilteredFlux(user));
        return liveMessagesSubscribers.get(user);
    }

    private Flux<Tweet> getFilteredFlux(String user) {
        return processor.filter(t ->
                usersDao.getFollowed(t.getAuthorId()).stream().
                        anyMatch(u -> u.getUser().equals(user) && u.getStartFollowDate().isBefore(t.getUpdatedDate()))).log();
    }

}
