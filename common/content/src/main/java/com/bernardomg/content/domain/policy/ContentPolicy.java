package com.bernardomg.content.domain.policy;

public interface ContentPolicy {

    void validate(long size, String mediaType);

}
