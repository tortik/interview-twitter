package com.interview.twitter.service.assembler;

import com.interview.twitter.domain.Tweet;
import com.interview.twitter.domain.TweetResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;


public class TweetResourceAssembler extends ResourceAssemblerSupport<Tweet, TweetResource> {

    public TweetResourceAssembler(Class<?> controllerClass, Class<TweetResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public TweetResource toResource(Tweet tweet) {
        TweetResource resource = new TweetResource(tweet.getTweetId(), tweet.getAuthorId(), tweet.getText());
        resource.setUpdatedDate(tweet.getUpdatedDate());
        resource.setCreatedDate(tweet.getCreatedDate());
        return resource;
    }

}
