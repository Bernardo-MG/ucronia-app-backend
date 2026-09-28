
package com.bernardomg.file.adapter.outbound.rest.security;

import com.bernardomg.file.domain.model.File;

public interface FileReadAuthorizer {

    public boolean canReadPrivateFiles();

    public void checkCanRead(final File file);

}
