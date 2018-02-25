package com.interview.twitter.service

import com.interview.twitter.dao.TweetsDao
import com.interview.twitter.dao.UserDao
import com.interview.twitter.domain.FollowUser
import com.interview.twitter.domain.Tweet
import org.mockito.Mockito
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

import static org.mockito.Mockito.timeout


class FeedServiceSpec extends Specification {


    def "should push tweet to feed and follower should get message"() {
        given:
        def usersDao = Mock(UserDao);
        def tweetDao = Mock(TweetsDao);
        def feedService = new FeedService()
        feedService.usersDao = usersDao;
        feedService.tweetsDao = tweetDao;
        usersDao.getFollowed("author") >> [new FollowUser("follower")]

        Tweet tweet = new Tweet("id", "author", "message");
        def listener = feedService.subscribeOnLiveMessages("follower");
        when:
        feedService.pushTweetToLive(tweet);
        feedService.processor.onComplete();
        then:
        StepVerifier.create(listener).expectNext(tweet).expectComplete().verify();

    }


    def "should store tweet to feed async"() {
        given:
        def usersDao = Mock(UserDao);
        def tweetDao = Mockito.mock(TweetsDao);
        def feedService = new FeedService()
        feedService.usersDao = usersDao;
        feedService.tweetsDao = tweetDao;
        usersDao.getFollowed("author") >> [new FollowUser("follower")]

        Tweet tweet = new Tweet("id", "author", "message");

        when:
        feedService.storeAsyncFeedTweet(tweet);
        then:
        Mockito.verify(tweetDao, timeout(100)).storeFeedTweets("follower", tweet);
    }

}
