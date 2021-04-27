package com.mbb.coffee;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.mbb.coffee");

        noClasses()
            .that()
            .resideInAnyPackage("com.mbb.coffee.service..")
            .or()
            .resideInAnyPackage("com.mbb.coffee.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.mbb.coffee.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
