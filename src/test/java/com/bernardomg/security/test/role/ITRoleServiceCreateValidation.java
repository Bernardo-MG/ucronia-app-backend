
package com.bernardomg.security.test.role;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoPrivilege;
import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.Privilege;
import com.bernardomg.security.service.RoleService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Role service - create validation")
public class ITRoleServiceCreateValidation {

    @Autowired
    private RoleService service;

    public ITRoleServiceCreateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the name already exists")
    public void testCreate_ExistingName() {
        final DtoRole    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoRole();
        data.setName("ADMIN");

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.name.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the name is not received")
    public void testCreate_MissingName() {
        final DtoRole    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoRole();
        data.setName(null);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.name.missing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the privilege doesn't exists")
    public void testCreate_NotExistingPrivilege() {
        final DtoRole               data;
        final DtoPrivilege          privilege;
        final Executable            executable;
        final Exception             exception;
        final Collection<Privilege> privileges;

        privilege = new DtoPrivilege();
        privilege.setId(111L);

        data = new DtoRole();
        data.setName("ADMIN");

        privileges = new ArrayList<>();
        privileges.add(privilege);

        data.setPrivileges(privileges);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.privilege.notExisting", exception.getMessage());
    }

}
