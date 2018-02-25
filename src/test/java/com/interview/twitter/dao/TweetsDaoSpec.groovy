package com.interview.twitter.dao

import com.interview.twitter.domain.Tweet
import spock.lang.Specification


class TweetsDaoSpec extends Specification {

    def "should store tweet "() {
        given:
        def tweetDao = new TweetsDao()
        def tweet = new Tweet("id", "author", "message");
        when:
        tweetDao.storeTweet("user", tweet);
        then:
        tweetDao.getTweet("id");
    }

    def "should get my tweets"() {
        given:
        def tweetDao = new TweetsDao()
        def tweet = new Tweet("id", "author", "message");
        when:
        tweetDao.storeTweet("user", tweet);
        then:
        tweetDao.getMyTweets("user").contains(tweet);
    }

    def "should store feed tweets"() {
        given:
        def tweetDao = new TweetsDao()
        def tweet = new Tweet("id", "author", "message");
        when:
        tweetDao.storeFeedTweets("user", tweet);
        then:
        tweetDao.getFeedTweets("user").contains(tweet);
    }

    def "should remove tweet "() {
        given:
        def tweetDao = new TweetsDao()
        def tweet = new Tweet("id", "author", "message");
        when:
        tweetDao.storeTweet("user", tweet);
        then:
        tweetDao.removeTweet("user", tweet);
        tweetDao.getTweet("id") == null;
        tweetDao.getMyTweets("author").empty;
    }

}
