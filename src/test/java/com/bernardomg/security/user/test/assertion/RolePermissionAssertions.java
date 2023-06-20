
package com.bernardomg.security.user.test.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.security.user.persistence.model.PersistentRolePermission;

public final class RolePermissionAssertions {

    public static final void isEqualTo(final PersistentRolePermission received,
            final PersistentRolePermission expected) {
        Assertions.assertThat(received.getActionId())
            .withFailMessage("Expected id to not be null")
            .isEqualTo(expected.getActionId());
        Assertions.assertThat(received.getResourceId())
            .withFailMessage("Expected resource id '%s' but got '%s'", expected.getResourceId(),
                received.getResourceId())
            .isEqualTo(expected.getResourceId());
        Assertions.assertThat(received.getRoleId())
            .withFailMessage("Expected role id '%s' but got '%s'", expected.getRoleId(), received.getRoleId())
            .isEqualTo(expected.getRoleId());
        Assertions.assertThat(received.getGranted())
            .withFailMessage("Expected granted flag '%s' but got '%s'", expected.getGranted(), received.getGranted())
            .isEqualTo(expected.getGranted());
    }

}
