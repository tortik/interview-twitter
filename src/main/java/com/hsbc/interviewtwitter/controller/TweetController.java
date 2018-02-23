package com.hsbc.interviewtwitter.controller;

import com.hsbc.interviewtwitter.service.TweetsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;


/**
 * Handles REST API related to tweets feed
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/")
@Validated
public class TweetController {

    @Autowired
    private TweetsService tweetsService;


    @RequestMapping(value = "users/{currentUser}/tweets", method = RequestMethod.POST)
    public ResponseEntity<Void> publishTweet(@PathVariable("currentUser") String currentUser,
                                             @Valid @Size(max = 140) @RequestBody String tweetText) {
        log.debug("User {} wants to tweet text {}", currentUser, tweetText);

        tweetsService.createTweet(currentUser, tweetText);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "users/{currentUser}/tweets/{tweetId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateTweet(@PathVariable("currentUser") String currentUser,
                                            @PathVariable String tweetId,
                                            @RequestBody String tweetText) {
        log.debug("User {} wants to update tweet {} with {]", currentUser, tweetId, tweetText);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "users/{currentUser}/tweets/{tweetId}", method = RequestMethod.POST, params = "retweet=true")
    public ResponseEntity<Void> reTweet(@PathVariable("currentUser") String currentUser, @PathVariable("tweeId") String tweeId) {
        log.debug("User {} wants to reTweet {}", currentUser, tweeId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "users/{currentUser}/tweets/{tweetId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeTweet(@PathVariable("currentUser") String user, @PathVariable("tweeId") String tweeId) {

        log.debug("User {} wants to remove tweet {}", user, tweeId);

        return ResponseEntity.noContent().build();
    }

}
