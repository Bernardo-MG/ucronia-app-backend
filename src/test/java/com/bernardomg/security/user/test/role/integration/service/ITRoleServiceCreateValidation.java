
package com.bernardomg.security.user.test.role.integration.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.request.DtoRoleCreateRequest;
import com.bernardomg.security.user.model.request.RoleCreateRequest;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

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
        final ThrowingCallable  executable;
        final FieldFailure      failure;
        final RoleCreateRequest data;

        data = DtoRoleCreateRequest.builder()
            .name("ADMIN")
            .build();

        executable = () -> service.create(data);

        failure = FieldFailure.of("name.existing", "name", "existing", "ADMIN");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

}
