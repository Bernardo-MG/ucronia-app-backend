
package com.bernardomg.asset.adapter.outbound.rest.security;

import com.bernardomg.asset.domain.model.Asset;

public interface AssetReadAuthorizer {

    public boolean canReadPrivate();

    public void checkCanRead(final Asset asset);

}
