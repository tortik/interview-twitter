package com.interview.twitter.dao

import spock.lang.Specification


class UserDaoSpec extends Specification {

    def "should add current user to followed list for specified followers"() {
        given:
        def userDao = new UserDao()
        when:
        userDao.addFollowers("first", ["second", "third"])
        then:
        userDao.getFollowed("second").size() == 1;
        userDao.getFollowed("third").size() == 1;
        userDao.getFollowed("second").every {user: "first"};
        userDao.getFollowed("third").every {user: "first"};
    }

    def "should unfollow user"() {
        given:
        def userDao = new UserDao()
        userDao.addFollowers("first", ["second"])
        when:
        userDao.unFollow("first", "second")
        then:
        userDao.getFollowed("second").size() == 0;
    }

}
