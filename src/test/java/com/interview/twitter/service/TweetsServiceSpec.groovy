package com.interview.twitter.service

import com.interview.twitter.common.exception.ResourceNotFoundException
import com.interview.twitter.dao.TweetsDao
import com.interview.twitter.domain.Tweet
import spock.lang.Specification


class TweetsServiceSpec extends Specification {

    def tweetService, tweetDao, feedService;

    def setup() {
        tweetService = new TweetsService();
        tweetDao = Mock(TweetsDao);
        feedService = Mock(FeedService);
        tweetService.tweetsDao = tweetDao;
        tweetService.feedService = feedService;
    }

    def "should create tweet "() {
        when:
        Tweet tweet = tweetService.createTweet("user", "tweet message");
        then:
        with(tweet) {
            originalId == tweet.tweetId
            text == "tweet message"
            authorId == "user"
            createdDate == updatedDate
        }

        1 * tweetDao.storeTweet("user", { it.text == "tweet message" && it.authorId == "user" });
        1 * feedService.storeAsyncFeedTweet({ it.text == "tweet message" && it.authorId == "user" });
    }

    def "shouldn't update tweet, can't find it by id "() {
        given:
        Tweet oldTweet = tweetService.createTweet("user", "old message");
        when:
        tweetService.updateTweet(oldTweet.authorId, oldTweet.tweetId, "updated tweet message");
        then:
        thrown(ResourceNotFoundException)
    }

    def "shouldn't update tweet, cause author and user mismatch"() {
        given:
        Tweet oldTweet = tweetService.createTweet("user", "old message");
        tweetDao.getTweet(_) >> oldTweet;
        when:
        tweetService.updateTweet("newUser", oldTweet.tweetId, "updated tweet message");
        then:
        thrown(ResourceNotFoundException)
    }

    def "should update tweet "() {
        given:
        Tweet oldTweet = tweetService.createTweet("user", "old message");
        tweetDao.getTweet(_) >> oldTweet;
        when:
        Tweet tweet = tweetService.updateTweet(oldTweet.authorId, oldTweet.tweetId, "updated tweet message");
        then:
        with(tweet) {
            originalId == oldTweet.tweetId
            text == "updated tweet message"
            authorId == oldTweet.authorId
            createdDate == oldTweet.createdDate
            createdDate != updatedDate
        }
    }

    def "should remove tweet. On next get will throw ResourceNotFoundException"() {
        given:
        Tweet oldTweet = tweetService.createTweet("user", "old message");
        tweetDao.getTweet(_) >>> [oldTweet, null];

        when:
        tweetService.removeTweet(oldTweet.authorId, oldTweet.tweetId);
        tweetService.getTweet(oldTweet.authorId, oldTweet.tweetId);
        then:
        thrown(ResourceNotFoundException)
    }

    def "should get user tweets"() {
        given:
        Tweet oldTweet = tweetService.createTweet("user", "old message");
        tweetDao.getMyTweets(_) >> [oldTweet];

        when:
        List<Tweet> myTweets = tweetService.getMyTweets(oldTweet.authorId);
        then:
        myTweets.contains(oldTweet)
        myTweets.size() == 1
    }

}
