
package com.bernardomg.association.test.architecture.rule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.bernardomg.association.test.architecture.predicate.Predicates;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

public final class ValidationRules {

    @ArchTest
    static final ArchRule validator_rules_should_be_in_validation_package = classes()
        .that(Predicates.areValidatorRuleClasses())
        .should()
        .resideInAPackage("..validation..")
        .allowEmptyShould(true);

    @ArchTest
    static final ArchRule validator_rules_should_be_suffixed              = classes()
        .that(Predicates.areValidatorRuleClasses())
        .should()
        .haveSimpleNameEndingWith("Rule")
        .allowEmptyShould(true);

    @ArchTest
    static final ArchRule validators_should_be_in_validation_package      = classes()
        .that(Predicates.areValidatorClasses())
        .should()
        .resideInAPackage("..validation..")
        .allowEmptyShould(true);

    @ArchTest
    static final ArchRule validators_should_be_suffixed                   = classes()
        .that(Predicates.areValidatorClasses())
        .should()
        .haveSimpleNameEndingWith("Validator")
        .allowEmptyShould(true);

}
