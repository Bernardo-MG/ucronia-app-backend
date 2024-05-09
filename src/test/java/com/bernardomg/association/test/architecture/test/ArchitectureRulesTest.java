
package com.bernardomg.association.test.architecture.test;

import com.bernardomg.association.test.architecture.rule.CacheRules;
import com.bernardomg.association.test.architecture.rule.CodingRules;
import com.bernardomg.association.test.architecture.rule.ConfigurationRules;
import com.bernardomg.association.test.architecture.rule.ControllerRules;
import com.bernardomg.association.test.architecture.rule.JpaEntityRules;
import com.bernardomg.association.test.architecture.rule.RepositoryRules;
import com.bernardomg.association.test.architecture.rule.SecurityRules;
import com.bernardomg.association.test.architecture.rule.ServiceRules;
import com.bernardomg.association.test.architecture.rule.TransactionalRules;
import com.bernardomg.association.test.architecture.rule.ValidationRules;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(
        packages = { "com.bernardomg.association", "com.bernardomg.async", "com.bernardomg.configuration",
                "com.bernardomg.email", "com.bernardomg.exception", "com.bernardomg.jpa" },
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureRulesTest {

    @ArchTest
    static final ArchTests cacheRules         = ArchTests.in(CacheRules.class);

    @ArchTest
    static final ArchTests codingRules        = ArchTests.in(CodingRules.class);

    @ArchTest
    static final ArchTests configurationRules = ArchTests.in(ConfigurationRules.class);

    @ArchTest
    static final ArchTests controllerRules    = ArchTests.in(ControllerRules.class);

    @ArchTest
    static final ArchTests jpaEntityRules     = ArchTests.in(JpaEntityRules.class);

    @ArchTest
    static final ArchTests repositoryRules    = ArchTests.in(RepositoryRules.class);

    @ArchTest
    static final ArchTests securityRules      = ArchTests.in(SecurityRules.class);

    @ArchTest
    static final ArchTests serviceRules       = ArchTests.in(ServiceRules.class);

    @ArchTest
    static final ArchTests transactionalRules = ArchTests.in(TransactionalRules.class);

    @ArchTest
    static final ArchTests validationRules    = ArchTests.in(ValidationRules.class);

}
