package com.hsbc.interviewtwitter.service.assembler;

import com.hsbc.interviewtwitter.domain.Tweet;
import com.hsbc.interviewtwitter.domain.TweetResource;
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
