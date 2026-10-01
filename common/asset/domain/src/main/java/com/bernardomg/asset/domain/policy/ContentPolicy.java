
package com.bernardomg.asset.domain.policy;

public interface ContentPolicy {

    void validate(long size, String mediaType);

}
