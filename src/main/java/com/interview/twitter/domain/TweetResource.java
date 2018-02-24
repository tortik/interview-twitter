package com.interview.twitter.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * used for Hateoas resource
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
    private String createdDate;
    private String updatedDate;

    public TweetResource(String id, String authorId, String text) {
        this.tweetId = id;
        this.originalId = id;
        this.text = text;
        this.authorId = authorId;
        this.originalAuthorId = authorId;
    }

    public void setUpdatedDate(ZonedDateTime dateTime) {
        this.updatedDate = Optional.ofNullable(dateTime).map(d -> d.format(DateTimeFormatter.ISO_DATE_TIME)).orElse(null);
    }

    public void setCreatedDate(ZonedDateTime dateTime) {
        this.createdDate = Optional.ofNullable(dateTime).map(d -> d.format(DateTimeFormatter.ISO_DATE_TIME)).orElse(null);
    }

}
