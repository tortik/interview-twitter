package com.hsbc.interviewtwitter.controller;

import com.hsbc.interviewtwitter.domain.Tweet;
import com.hsbc.interviewtwitter.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Handles REST API related to user feed
 */
@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class FeedController {

    @Autowired
    private FeedService feedService;

    @RequestMapping(value = "users/{currentUser}/feed", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> tweetsFeed(@PathVariable("currentUser") String currentUser){
        log.debug("User {} accessing his feed {}", currentUser);
        return feedService.getFeedForUser(currentUser);
    }

    @RequestMapping(value = "users/{currentUser}/feed", method = RequestMethod.GET, params = "my_tweets=true")
    public ResponseEntity<List<Tweet>> myTweetsFeed(@PathVariable("currentUser") String currentUser){
        log.debug("User {} wants to get own tweets {}", currentUser);

        return ResponseEntity.ok().build();
    }

}
