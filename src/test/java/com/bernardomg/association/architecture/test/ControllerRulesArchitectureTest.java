
package com.bernardomg.association.architecture.test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class ControllerRulesArchitectureTest {

    @ArchTest
    static final ArchRule controllers_should_be_in_controller_package = classes().that()
        .areAnnotatedWith(RestController.class)
        .should()
        .resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule controllers_should_be_suffixed              = classes().that()
        .areAnnotatedWith(RestController.class)
        .should()
        .haveSimpleNameEndingWith("Controller");

}
