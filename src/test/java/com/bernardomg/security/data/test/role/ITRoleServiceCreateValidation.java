
package com.bernardomg.security.data.test.role;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoRole;
import com.bernardomg.security.data.service.RoleService;
import com.bernardomg.validation.exception.ValidationException;

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
        final Executable executable;
        final Exception  exception;
        final DtoRole    data;

        data = new DtoRole();
        data.setName("ADMIN");

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.name.existing", exception.getMessage());
    }

}
