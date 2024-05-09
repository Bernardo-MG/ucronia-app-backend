
package com.bernardomg.association.test.architecture.test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg", importOptions = ImportOption.DoNotIncludeTests.class)
public class ModulesArchitectureRulesTest {

    @ArchTest
    static final ArchRule module_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()

        .layer("Members")
        .definedBy("com.bernardomg.association.member..")
        .layer("Transactions")
        .definedBy("com.bernardomg.association.transaction..")
        .layer("Fees")
        .definedBy("com.bernardomg.association.fee..")
        .layer("Library")
        .definedBy("com.bernardomg.association.library..")
        .layer("Users")
        .definedBy("com.bernardomg.association.security.user..")
        .layer("Account")
        .definedBy("com.bernardomg.association.security.account..")

        .whereLayer("Members")
        .mayOnlyBeAccessedByLayers("Fees", "Users", "Library", "Account")
        .whereLayer("Transactions")
        .mayOnlyBeAccessedByLayers("Fees")
        .whereLayer("Fees")
        .mayNotBeAccessedByAnyLayer()
        .whereLayer("Library")
        .mayNotBeAccessedByAnyLayer()
        .whereLayer("Users")
        .mayOnlyBeAccessedByLayers("Account", "Fees");

}
