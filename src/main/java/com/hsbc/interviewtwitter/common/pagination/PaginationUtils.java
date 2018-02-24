package com.hsbc.interviewtwitter.common.pagination;


import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


public class PaginationUtils {


    public static <T, R extends ResourceSupport> PagedResources<R> getPagedResource(PaginationContext<T, R> ctx) {
        int subFrom = (ctx.getPage() - 1) * ctx.getSize();
        int subTo = ctx.getPage() * ctx.getSize();
        List<T> content = ctx.getContent();
        List<T> contents = Optional.of(content).filter(CollectionUtils::isEmpty).
                orElseGet(() -> content.subList(subFrom, Math.min(content.size(), subTo)));

        long totalElements = content.size();

        List<R> resources = ctx.getAssembler().toResources(contents);

        PagedResources.PageMetadata pageMetadata = new PagedResources.PageMetadata(ctx.getSize(), ctx.getPage(), totalElements);

        return new PagedResources<>(resources, pageMetadata);
    }


    private PaginationUtils() {
    }
}
