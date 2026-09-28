
package com.bernardomg.image.adapter.outbound.rest.security;

import com.bernardomg.asset.domain.model.Asset;

public interface ImageReadAuthorizer {

    public boolean canReadPrivateImages();

    public void checkCanRead(final Asset image);

}
