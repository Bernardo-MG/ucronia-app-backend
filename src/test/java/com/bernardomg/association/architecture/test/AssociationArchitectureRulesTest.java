
package com.bernardomg.association.architecture.test;

import com.bernardomg.association.architecture.rule.CacheRules;
import com.bernardomg.association.architecture.rule.JpaEntityRules;
import com.bernardomg.association.architecture.rule.RepositoryRules;
import com.bernardomg.association.architecture.rule.ServiceRules;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class AssociationArchitectureRulesTest {

    @ArchTest
    static final ArchTests cacheRules      = ArchTests.in(CacheRules.class);

    @ArchTest
    static final ArchTests jpaEntityRules  = ArchTests.in(JpaEntityRules.class);

    @ArchTest
    static final ArchTests repositoryRules = ArchTests.in(RepositoryRules.class);

    @ArchTest
    static final ArchTests serviceRules    = ArchTests.in(ServiceRules.class);

}
