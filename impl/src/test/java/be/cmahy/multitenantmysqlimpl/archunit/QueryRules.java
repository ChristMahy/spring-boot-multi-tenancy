package be.cmahy.multitenantmysqlimpl.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
    packages = {"be.cmahy.multitenantmysqlimpl"},
    importOptions = {
        ImportOption.DoNotIncludeTests.class,
        ImportOption.DoNotIncludeArchives.class,
        ImportOption.DoNotIncludeJars.class
    }
)
public class QueryRules {
    static final String PACKAGE = "be.cmahy.multitenantmysqlimpl.application.query..";

//    @ArchTest
//    public static final ArchRule onlyClassesWithQueryAnnotationAreAllowed = classes()
//        .that().resideInAPackage(PACKAGE)
//        .should().beAnnotatedWith();

    @ArchTest
    public static final ArchRule allClassesShouldEndWithQuerySuffix = classes()
        .that().resideInAPackage(PACKAGE)
        .should().haveSimpleNameEndingWith("Query");

//    @ArchTest
//    public static final ArchRule onlyQueryAnnotationIsAllowedInRightPackage = classes()
//        .that().areAnnotatedWith()
//        .should().resideInAPackage(PACKAGE);
}
