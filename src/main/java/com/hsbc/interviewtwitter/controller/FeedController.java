package com.hsbc.interviewtwitter.controller;

import com.google.common.collect.Lists;
import com.hsbc.interviewtwitter.common.pagination.PaginationContext;
import com.hsbc.interviewtwitter.common.pagination.PaginationUtils;
import com.hsbc.interviewtwitter.domain.Tweet;
import com.hsbc.interviewtwitter.domain.TweetResource;
import com.hsbc.interviewtwitter.service.FeedService;
import com.hsbc.interviewtwitter.service.assembler.TweetResourceAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Handles REST API related to user feed
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @RequestMapping(value = "users/{currentUser}/feed", params = "live=true", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> tweetsFeed(@PathVariable("currentUser") String currentUser) {
        log.debug("User {} accessing his feed {}", currentUser);
        return feedService.subscribeOnLiveMessages(currentUser);
    }

    @RequestMapping(value = "users/{currentUser}/feed", params = {"page", "size"}, method = RequestMethod.GET)
    public ResponseEntity<PagedResources<TweetResource>> myFeed(@PathVariable("currentUser") String currentUser,
                                              @Valid @Min(value = 1) @RequestParam(defaultValue = "1") int page,
                                              @Valid @Min(value = 0) @RequestParam(defaultValue = "50") int size) {

        log.debug("User {} wants to get own tweets {}", currentUser);
        List<Tweet> tweets = feedService.getFeedTweets(currentUser);

        PaginationContext<Tweet, TweetResource> context = PaginationContext.of(tweets, currentUser, page, size,
                new TweetResourceAssembler(Tweet.class, TweetResource.class));
        PagedResources<TweetResource> pagedResources = PaginationUtils.getPagedResource(context);
        pagedResources.add(getLinks(currentUser, pagedResources.getMetadata()));

        return ResponseEntity.ok(pagedResources);
    }

    private List<Link> getLinks(String currentUser, PagedResources.PageMetadata pm) {
        List<Link> links = Lists.newArrayList();
        if (pm.getNumber() > 1) {
            Link prev = linkTo(methodOn(this.getClass()).
                    myFeed(currentUser, (int) pm.getNumber() - 1, (int) pm.getSize())).withRel("prev");
            links.add(prev);
        }
        if (pm.getTotalPages() > pm.getNumber()) {
            Link next = linkTo(methodOn(this.getClass()).
                    myFeed(currentUser, (int) pm.getNumber() + 1, (int) pm.getSize())).withRel("next");
            links.add(next);
        }
        return links;
    }


}
