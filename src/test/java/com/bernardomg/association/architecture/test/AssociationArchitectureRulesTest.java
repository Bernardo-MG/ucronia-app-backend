
package com.bernardomg.association.architecture.test;

import com.bernardomg.association.architecture.rule.CacheArchitectureRules;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class AssociationArchitectureRulesTest {

    @ArchTest
    static final ArchTests cacheRules = ArchTests.in(CacheArchitectureRules.class);

}
