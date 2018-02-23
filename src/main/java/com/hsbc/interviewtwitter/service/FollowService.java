package com.hsbc.interviewtwitter.service;

import com.hsbc.interviewtwitter.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FollowService {

    @Autowired
    private UserDao userDao;

    public void followUser(String currentUser, List<String> followUsers){
        userDao.addFollowed(currentUser, followUsers);
        log.debug("User {} now follow {} ", currentUser, followUsers);

    }

}
