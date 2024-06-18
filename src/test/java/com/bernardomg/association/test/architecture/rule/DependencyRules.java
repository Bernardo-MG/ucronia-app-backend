
package com.bernardomg.association.test.architecture.rule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

public class DependencyRules {

    @ArchTest
    static final ArchRule no_java_util_logging   = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    @ArchTest
    static final ArchRule no_jodatime            = NO_CLASSES_SHOULD_USE_JODATIME;

    @ArchTest
    static final ArchRule not_import_log4j_utils = noClasses().should()
        .dependOnClassesThat()
        .resideInAnyPackage("org.apache.logging.log4j.util..");

}
