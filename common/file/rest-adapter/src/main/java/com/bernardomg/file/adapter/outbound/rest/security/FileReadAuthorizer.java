
package com.bernardomg.file.adapter.outbound.rest.security;

import com.bernardomg.asset.domain.model.Asset;

public interface FileReadAuthorizer {

    public boolean canReadPrivateFiles();

    public void checkCanRead(final Asset file);

}
