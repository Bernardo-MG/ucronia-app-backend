
package com.bernardomg.security.token.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.service.DefaultTokenCleanUpService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DefaultTokenCleanUpService")
public class ITTokenCleanUpService {

    @Autowired
    private DefaultTokenCleanUpService service;

    @Autowired
    private TokenRepository            tokenRepository;

    @Test
    @DisplayName("Removes consumed tokens")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/consumed.sql" })
    void testCleanUpTokens_Consumed() {
        final long count;

        service.cleanUpTokens();

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("Does nothing when there are no tokens")
    void testCleanUpTokens_Empty() {
        final long count;

        service.cleanUpTokens();

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("Removes expired tokens")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testCleanUpTokens_Expired() {
        final long count;

        service.cleanUpTokens();

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("Removes revoked tokens")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/revoked.sql" })
    void testCleanUpTokens_Revoked() {
        final long count;

        service.cleanUpTokens();

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("Does not remove valid tokens")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testCleanUpTokens_Valid() {
        final long count;

        service.cleanUpTokens();

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

}
