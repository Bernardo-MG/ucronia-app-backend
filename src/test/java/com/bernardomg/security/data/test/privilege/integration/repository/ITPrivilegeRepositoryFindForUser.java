
package com.bernardomg.security.data.test.privilege.integration.repository;

import java.util.Collection;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.persistence.model.PersistentPrivilege;
import com.bernardomg.security.data.persistence.repository.PrivilegeRepository;

@IntegrationTest
@DisplayName("Privilege repository - find for user")
public class ITPrivilegeRepositoryFindForUser {

    @Autowired
    private PrivilegeRepository repository;

    public ITPrivilegeRepositoryFindForUser() {
        super();
    }

    @Test
    @DisplayName("Finds all the privileges for the user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindForUser_Count() {
        final Collection<PersistentPrivilege> read;

        read = repository.findForUser(1L);

        Assertions.assertEquals(4, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no privileges when the user has none")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindForUser_NoPrivileges_Count() {
        final Collection<PersistentPrivilege> read;

        read = repository.findForUser(1L);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no privileges for a not existing user")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testFindForUser_NotExisting_Count() {
        final Collection<PersistentPrivilege> read;

        read = repository.findForUser(-1L);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

}
