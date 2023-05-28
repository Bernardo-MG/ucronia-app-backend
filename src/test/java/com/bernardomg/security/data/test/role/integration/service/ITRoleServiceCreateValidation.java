
package com.bernardomg.security.data.test.role.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.ImmutableRole;
import com.bernardomg.security.data.service.RoleService;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

@IntegrationTest
@DisplayName("Role service - create validation")
@Sql({ "/db/queries/security/role/single.sql" })
public class ITRoleServiceCreateValidation {

    @Autowired
    private RoleService service;

    public ITRoleServiceCreateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the name already exist")
    public void testCreate_NameExists() {
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;
        final ImmutableRole         data;

        data = ImmutableRole.builder()
            .name("ADMIN")
            .build();

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("existing", failure.getCode());
        Assertions.assertEquals("name", failure.getField());
        Assertions.assertEquals("name.existing", failure.getMessage());
    }

}
