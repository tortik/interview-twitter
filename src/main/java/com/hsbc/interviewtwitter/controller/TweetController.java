package com.hsbc.interviewtwitter.controller;

import com.google.common.collect.Lists;
import com.hsbc.interviewtwitter.common.pagination.PaginationContext;
import com.hsbc.interviewtwitter.common.pagination.PaginationUtils;
import com.hsbc.interviewtwitter.domain.Tweet;
import com.hsbc.interviewtwitter.domain.TweetResource;
import com.hsbc.interviewtwitter.service.TweetsService;
import com.hsbc.interviewtwitter.service.assembler.TweetResourceAssembler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


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
    @Autowired
    EntityLinks entityLinks;

    @RequestMapping(value = "users/{currentUser}/tweets", params = {"page", "size"}, method = RequestMethod.GET)
    public ResponseEntity<PagedResources<TweetResource>> getUserTweets(@PathVariable("currentUser") String currentUser,
                                                                       @Valid @Min(value = 1) @RequestParam(defaultValue = "1") int page,
                                                                       @Valid @Min(value = 0) @RequestParam(defaultValue = "50") int size) {
        log.debug("User {} wants to tweet text {}", currentUser);

        List<Tweet> tweets = tweetsService.getMyTweets(currentUser);

        PaginationContext<Tweet, TweetResource> context = PaginationContext.of(tweets, currentUser, page, size,
                new TweetResourceAssembler(Tweet.class, TweetResource.class));
        PagedResources<TweetResource> pagedResources = PaginationUtils.getPagedResource(context);
        pagedResources.add(getLinks(currentUser, pagedResources.getMetadata()));

        return ResponseEntity.ok(pagedResources);
    }

    @RequestMapping(value = "users/{currentUser}/tweets", method = RequestMethod.POST)
    public ResponseEntity<Tweet> publishTweet(@PathVariable("currentUser") String currentUser,
                                              @Valid @Size(max = 140) @RequestBody String tweetText) {
        log.debug("User {} wants to tweet text {}", currentUser, tweetText);

        Tweet tweet = tweetsService.createTweet(currentUser, tweetText);

        return ResponseEntity.ok(tweet);
    }

    @RequestMapping(value = "users/{currentUser}/tweets/{tweetId}", method = RequestMethod.PUT)
    public ResponseEntity<Tweet> updateTweet(@PathVariable("currentUser") String currentUser,
                                             @PathVariable String tweetId,
                                             @RequestBody String tweetText) {
        log.debug("User {} wants to update tweet {} with {]", currentUser, tweetId, tweetText);

        Tweet updatedTweet = tweetsService.updateTweet(currentUser, tweetId, tweetText);

        return ResponseEntity.ok(updatedTweet);
    }

    @RequestMapping(value = "users/{currentUser}/tweets/{tweetId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeTweet(@PathVariable("currentUser") String user, @PathVariable("tweeId") String tweeId) {

        log.debug("User {} wants to remove tweet {}", user, tweeId);

        tweetsService.removeTweet(user, tweeId);

        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = "users/{currentUser}/tweets/{tweetId}", method = RequestMethod.POST, params = "retweet=true")
    public ResponseEntity<Void> reTweet(@PathVariable("currentUser") String currentUser, @PathVariable("tweeId") String tweeId) {
        log.debug("User {} wants to reTweet {}", currentUser, tweeId);
        throw new NotImplementedException("This part of functionality is not implemented yet");
    }

    private List<Link> getLinks(String currentUser, PagedResources.PageMetadata pm) {
        List<Link> links = Lists.newArrayList();
        if (pm.getNumber() > 1) {
            Link prev = linkTo(methodOn(this.getClass()).
                    getUserTweets(currentUser, (int) pm.getNumber() - 1, (int) pm.getSize())).withRel("prev");
            links.add(prev);
        }
        if (pm.getTotalPages() > pm.getNumber()) {
            Link next = linkTo(methodOn(this.getClass()).
                    getUserTweets(currentUser, (int) pm.getNumber() + 1, (int) pm.getSize())).withRel("next");
            links.add(next);
        }
        return links;
    }



}
