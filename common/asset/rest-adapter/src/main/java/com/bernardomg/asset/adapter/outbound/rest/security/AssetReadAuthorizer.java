
package com.bernardomg.asset.adapter.outbound.rest.security;

import com.bernardomg.asset.domain.model.Asset;

public interface AssetReadAuthorizer {

    public boolean canReadPrivateAssets();

    public void checkCanRead(final Asset asset);

}
