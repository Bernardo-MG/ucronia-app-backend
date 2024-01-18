
package com.bernardomg.association.architecture.test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class LayeredArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()

        .layer("Controllers")
        .definedBy("com.bernardomg.association..controller..")
        .layer("Services")
        .definedBy("com.bernardomg.association..service..")
        .layer("Persistence")
        .definedBy("com.bernardomg.association..persistence..")
        .layer("Configuration")
        .definedBy("com.bernardomg.association..config..")
        .layer("Validation")
        .definedBy("com.bernardomg.association..validation..")
        .layer("Schedule")
        .definedBy("com.bernardomg.association..schedule..")

        .whereLayer("Controllers")
        .mayNotBeAccessedByAnyLayer()
        .whereLayer("Services")
        .mayOnlyBeAccessedByLayers("Configuration", "Services", "Schedule", "Controllers")
        .whereLayer("Persistence")
        .mayOnlyBeAccessedByLayers("Configuration", "Validation", "Persistence", "Services")
        .whereLayer("Validation")
        .mayOnlyBeAccessedByLayers("Services");

}
