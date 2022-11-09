
package com.bernardomg.security.password.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.service.ResetPasswordService;

@IntegrationTest
@DisplayName("ResetPasswordService")
@Sql({ "/db/queries/security/user/single.sql" })
public class ITResetPasswordService {

    @Autowired
    private UserRepository       repository;

    @Autowired
    private ResetPasswordService service;

    public ITResetPasswordService() {
        super();
    }

    @Test
    @DisplayName("Updates the data")
    public void testChangePassword_PersistedData() {
        final PersistentUser entity;

        service.changePassword("admin", "1");
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("admin", entity.getUsername());
        Assertions.assertNotEquals("1", entity.getPassword());
        Assertions.assertEquals("email", entity.getEmail());
        Assertions.assertFalse(entity.getCredentialsExpired());
        Assertions.assertTrue(entity.getEnabled());
        Assertions.assertFalse(entity.getExpired());
        Assertions.assertFalse(entity.getLocked());
    }

    @Test
    @DisplayName("Returns the expected flag")
    public void testChangePassword_ReturnedData() {
        final Boolean changed;

        changed = service.changePassword("admin", "1");

        Assertions.assertTrue(changed);
    }

}
