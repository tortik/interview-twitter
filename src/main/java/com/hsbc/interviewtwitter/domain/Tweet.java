package com.hsbc.interviewtwitter.domain;

import com.google.common.collect.Lists;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

/**
 *
 */
@Getter
@Setter
@ToString
public class Tweet {
    private String id;
    private String text;
    private String authorId;
    private String originalId;
    private String originalAuthorId;

    public Tweet(String id, String authorId, String text) {
        this.id = id;
        this.originalId = id;
        this.text = text;
        this.authorId = authorId;
        this.originalAuthorId = authorId;
    }


}
