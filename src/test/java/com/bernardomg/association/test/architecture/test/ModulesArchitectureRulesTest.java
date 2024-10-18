
package com.bernardomg.association.test.architecture.test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = { "com.bernardomg.association", "com.bernardomg.async", "com.bernardomg.configuration",
        "com.bernardomg.email", "com.bernardomg.exception", "com.bernardomg.jpa", "com.bernardomg.settings",
        "com.bernardomg.event" }, importOptions = ImportOption.DoNotIncludeTests.class)
public class ModulesArchitectureRulesTest {

    @ArchTest
    static final ArchRule module_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()

        .layer("Persons")
        .definedBy("com.bernardomg.association.person..")
        .layer("Members")
        .definedBy("com.bernardomg.association.member..")
        .layer("Transactions")
        .definedBy("com.bernardomg.association.transaction..")
        .layer("Fees")
        .definedBy("com.bernardomg.association.fee..")
        .layer("Inventory")
        .definedBy("com.bernardomg.association.inventory..")

        // Security modules
        .layer("Users")
        .definedBy("com.bernardomg.association.security.user..")
        .layer("Account")
        .definedBy("com.bernardomg.association.security.account..")

        // Misc modules
        .layer("Settings")
        .definedBy("com.bernardomg.settings..")
        .layer("Association settings")
        .definedBy("com.bernardomg.association.settings..")
        .layer("Events")
        .definedBy("com.bernardomg.event..")
        .layer("Association events")
        .definedBy("com.bernardomg.association.event..")
        .layer("Executable")
        .definedBy("com.bernardomg.association")

        // Library modules
        .layer("Library authors")
        .definedBy("com.bernardomg.association.library.author..")
        .layer("Library books")
        .definedBy("com.bernardomg.association.library.book..")
        .layer("Library book types")
        .definedBy("com.bernardomg.association.library.booktype..")
        .layer("Library game systems")
        .definedBy("com.bernardomg.association.library.gamesystem..")
        .layer("Library publisher")
        .definedBy("com.bernardomg.association.library.publisher..")
        .layer("Library lending")
        .definedBy("com.bernardomg.association.library.lending..")

        .whereLayer("Persons")
        .mayOnlyBeAccessedByLayers("Members", "Users", "Account", "Fees", "Library books", "Library lending",
            "Inventory")
        .whereLayer("Members")
        .mayOnlyBeAccessedByLayers("Fees", "Inventory", "Account")
        .whereLayer("Transactions")
        .mayOnlyBeAccessedByLayers("Fees")
        .whereLayer("Fees")
        .mayNotBeAccessedByAnyLayer()

        // Security modules
        .whereLayer("Users")
        .mayOnlyBeAccessedByLayers("Account", "Fees")
        .whereLayer("Account")
        .mayNotBeAccessedByAnyLayer()

        // Misc modules
        .whereLayer("Settings")
        .mayOnlyBeAccessedByLayers("Executable", "Association settings", "Fees", "Members")
        .whereLayer("Association settings")
        .mayOnlyBeAccessedByLayers("Fees")
        .whereLayer("Events")
        .mayOnlyBeAccessedByLayers("Executable", "Association events", "Members", "Fees", "Persons")
        .whereLayer("Association events")
        .mayOnlyBeAccessedByLayers("Members", "Fees", "Persons")

        // Library modules
        .whereLayer("Library authors")
        .mayOnlyBeAccessedByLayers("Library books")
        .whereLayer("Library books")
        .mayOnlyBeAccessedByLayers("Library lending")
        .whereLayer("Library book types")
        .mayOnlyBeAccessedByLayers("Library books")
        .whereLayer("Library game systems")
        .mayOnlyBeAccessedByLayers("Library books")
        .whereLayer("Library publisher")
        .mayOnlyBeAccessedByLayers("Library books")
        .whereLayer("Library lending")
        .mayOnlyBeAccessedByLayers("Library books");

}
