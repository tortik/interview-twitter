package com.interview.twitter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interview.twitter.common.rest.RestConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;

/**
 *
 */
@Getter
@Setter
@ToString
public class TweetResource extends ResourceSupport {

    private String tweetId;
    private String text;
    private String authorId;
    private String originalId;
    private String originalAuthorId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RestConfig.DATE_TIME_FORMAT)
    private ZonedDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RestConfig.DATE_TIME_FORMAT)
    private ZonedDateTime updatedDate;

    public TweetResource(String id, String authorId, String text) {
        this.tweetId = id;
        this.originalId = id;
        this.text = text;
        this.authorId = authorId;
        this.originalAuthorId = authorId;
    }

}
