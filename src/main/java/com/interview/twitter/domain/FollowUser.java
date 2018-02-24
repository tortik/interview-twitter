package com.interview.twitter.domain;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class FollowUser {
    @NonNull
    private String user;
    private ZonedDateTime startFollowDate = ZonedDateTime.now();

}
