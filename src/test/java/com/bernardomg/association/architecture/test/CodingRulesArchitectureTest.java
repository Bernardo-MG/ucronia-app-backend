
package com.bernardomg.association.architecture.test;

import static com.tngtech.archunit.library.DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class CodingRulesArchitectureTest {

    @ArchTest
    static final ArchRule  no_accesses_to_upper_package = NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

    @ArchTest
    private final ArchRule no_generic_exceptions        = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

}
