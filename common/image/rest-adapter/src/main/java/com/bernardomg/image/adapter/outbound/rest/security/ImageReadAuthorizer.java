
package com.bernardomg.image.adapter.outbound.rest.security;

import com.bernardomg.image.domain.model.Image;

public interface ImageReadAuthorizer {

    public boolean canReadPrivateImages();

    public void checkCanRead(final Image image);

}
