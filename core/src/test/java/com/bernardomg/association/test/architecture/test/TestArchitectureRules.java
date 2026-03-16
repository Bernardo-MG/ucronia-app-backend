
package com.bernardomg.association.test.architecture.test;

import com.bernardomg.association.test.architecture.rule.EntityRules;
import com.bernardomg.association.test.architecture.rule.SecurityRules;
import com.bernardomg.framework.testing.architecture.rule.CodingRules;
import com.bernardomg.framework.testing.architecture.rule.DependencyRules;
import com.bernardomg.framework.testing.architecture.rule.JpaEntityRules;
import com.bernardomg.framework.testing.architecture.rule.RepositoryRules;
import com.bernardomg.framework.testing.architecture.rule.ServiceRules;
import com.bernardomg.framework.testing.architecture.rule.TransactionalRules;
import com.bernardomg.framework.testing.architecture.rule.ValidationRules;
import com.bernardomg.framework.testing.architecture.rule.springframework.SpringControllerRules;
import com.bernardomg.framework.testing.architecture.rule.springframework.SpringRules;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(
        packages = { "com.bernardomg.association", "com.bernardomg.async", "com.bernardomg.configuration",
                "com.bernardomg.email", "com.bernardomg.exception", "com.bernardomg.jpa" },
        importOptions = ImportOption.DoNotIncludeTests.class)
public class TestArchitectureRules {

    @ArchTest
    static final ArchTests codingRules           = ArchTests.in(CodingRules.class);

    @ArchTest
    static final ArchTests dependencyRules       = ArchTests.in(DependencyRules.class);

    // TODO: merge with JpaEntityRules
    @ArchTest
    static final ArchTests entityRules           = ArchTests.in(EntityRules.class);

    @ArchTest
    static final ArchTests jpaEntityRules        = ArchTests.in(JpaEntityRules.class);

    @ArchTest
    static final ArchTests repositoryRules       = ArchTests.in(RepositoryRules.class);

    @ArchTest
    static final ArchTests securityRules         = ArchTests.in(SecurityRules.class);

    @ArchTest
    static final ArchTests serviceRules          = ArchTests.in(ServiceRules.class);

    @ArchTest
    static final ArchTests springControllerRules = ArchTests.in(SpringControllerRules.class);

    @ArchTest
    static final ArchTests springRules           = ArchTests.in(SpringRules.class);

    @ArchTest
    static final ArchTests transactionalRules    = ArchTests.in(TransactionalRules.class);

    @ArchTest
    static final ArchTests validationRules       = ArchTests.in(ValidationRules.class);

}
