package com.interview.twitter.service;

import com.interview.twitter.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FollowService {

    @Autowired
    private UserDao userDao;

    public void followUser(String currentUser, List<String> followUsers) {
        userDao.addFollowers(currentUser, followUsers);
        log.debug("User {} now follow {} ", currentUser, followUsers);

    }

    public void unFollowUser(String currentUser, String unFollow) {
        userDao.unFollow(currentUser, unFollow);
        log.debug("User {} successfully unFollow {} ", currentUser, unFollow);

    }

}
