
package com.bernardomg.association.architecture.test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class ClassNamingRulesArchitectureTest {

    @ArchTest
    public static final ArchRule services_should_be_suffixed    = classes().that()
        .resideInAPackage("..service..")
        .and()
        .doNotHaveModifier(JavaModifier.SYNTHETIC)
        .and()
        .haveSimpleNameNotEndingWith("package-info")
        .should()
        .haveSimpleNameEndingWith("Service");

    @ArchTest
    static final ArchRule        controllers_should_be_suffixed = classes().that()
        .areAnnotatedWith(RestController.class)
        .should()
        .haveSimpleNameEndingWith("Controller");

}
