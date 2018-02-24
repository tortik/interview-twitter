package com.interview.twitter.controller;

import com.interview.twitter.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles REST API related to following users
 */

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class FollowersController {


    @Autowired
    private FollowService followService;

    @RequestMapping(value = "users/{currentUser}/followers", method = RequestMethod.POST)
    public ResponseEntity<Void> followUsers(@PathVariable("currentUser") String currentUser, @RequestBody List<String> userIds) {
        log.debug("User {} wants to follow {}", currentUser, userIds);
        followService.followUser(currentUser, userIds);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "users/{currentUser}/followers/{unfollowUserId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> unFollowUser(@PathVariable("currentUser") String currentUser,
                                             @PathVariable("unfollowUserId") String unfollowUserId) {

        log.debug("User {} wants to unfollow {}", currentUser, unfollowUserId);
        followService.unFollowUser(currentUser, unfollowUserId);
        return ResponseEntity.noContent().build();
    }


}
