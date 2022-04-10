package be.cmahy.multitenantmysqlimpl.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import javax.inject.Inject;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(
    packages = {"be.cmahy.multitenantmysqlimpl"},
    importOptions = {
        ImportOption.DoNotIncludeTests.class,
        ImportOption.DoNotIncludeArchives.class,
        ImportOption.DoNotIncludeJars.class
    }
)
public class MainArchitecture {
    @ArchTest
    public static final ArchRule shouldRespectHexagonalArchitecture = layeredArchitecture()
        .layer("adapter").definedBy("..adapter..")
        .layer("application").definedBy("..application..")

        .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
        .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")

        .ignoreDependency(resideInAPackage("..exception.."), resideInAPackage("..adapter.."))
        .ignoreDependency(resideInAPackage("..exception.."), resideInAPackage("..application.."));

    @ArchTest
    public static final ArchRule noInjectedFields = noFields()
        .should().beAnnotatedWith(Autowired.class)
        .orShould().beAnnotatedWith(Inject.class)
        .because("Only constructor should be annotated with injection");

    @ArchTest
    public static final ArchRule noSpringDependencyInsideApplication = noClasses()
        .that().resideInAPackage("..application..")
        .should().accessClassesThat().resideInAPackage("..org.springframework..")
        .orShould().dependOnClassesThat().resideInAPackage("..org.springframework..");

    @ArchTest
    public static final ArchRule noSpringInjectionInsideAppliaction = noConstructors()
        .that().areDeclaredInClassesThat().resideInAPackage("..application..")
        .should().beAnnotatedWith(Autowired.class);

    @ArchTest
    public static final ArchRule noSpringBeanDeclarationFromApplication = noClasses()
        .that().resideInAPackage("..application..")
        .and().areNotInterfaces()
        .should().beAnnotatedWith(Controller.class)
        .orShould().beAnnotatedWith(Component.class)
        .orShould().beAnnotatedWith(Indexed.class)
        .orShould().beAnnotatedWith(Repository.class)
        .orShould().beAnnotatedWith(Service.class);
}
