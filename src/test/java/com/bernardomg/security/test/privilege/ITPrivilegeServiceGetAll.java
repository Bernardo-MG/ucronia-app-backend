
package com.bernardomg.security.test.privilege;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoPrivilege;
import com.bernardomg.security.model.Privilege;
import com.bernardomg.security.service.PrivilegeService;

@IntegrationTest
@DisplayName("Privilege service - get all")
@Sql({ "/db/queries/security/privilege/multiple.sql" })
public class ITPrivilegeServiceGetAll {

    @Autowired
    private PrivilegeService service;

    public ITPrivilegeServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<? extends Privilege> result;
        final DtoPrivilege                  sample;
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        sample = new DtoPrivilege();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(4, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<? extends Privilege> data;
        final DtoPrivilege                  sample;
        final Pageable                      pageable;
        final Collection<String>            names;

        pageable = Pageable.unpaged();

        sample = new DtoPrivilege();

        data = service.getAll(sample, pageable);

        names = StreamSupport.stream(data.spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(names.contains("CREATE_DATA"));
        Assertions.assertTrue(names.contains("READ_DATA"));
        Assertions.assertTrue(names.contains("UPDATE_DATA"));
        Assertions.assertTrue(names.contains("DELETE_DATA"));
    }

}
