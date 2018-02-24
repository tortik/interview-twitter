package com.hsbc.interviewtwitter.common.pagination;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class PaginationContext<T, R extends ResourceSupport> {

    private List<T> content;
    private String currentUser;
    private int page;
    private int size;
    private ResourceAssemblerSupport<T, R> assembler;
}
