package com.interview.twitter.service

import com.interview.twitter.dao.UserDao
import spock.lang.Specification


class FollowServiceSpec extends Specification {

    def "should follow users by calling userDao followUser and pass params"() {
        given:
        def userDao = Mock(UserDao)
        def followService = new FollowService()
        followService.userDao = userDao;
        when:
        followService.followUser("first", ["second", "third"])
        then:
        1 * userDao.addFollowers("first", ["second", "third"]);
    }

    def "should unfollow user by calling userDao unfollowUser and pass params"() {
        given:
        def userDao = Mock(UserDao)
        def followService = new FollowService()
        followService.userDao = userDao;
        when:
        followService.unFollowUser("first", "second")
        then:
        1 * userDao.unFollow("first", "second");
    }

}
