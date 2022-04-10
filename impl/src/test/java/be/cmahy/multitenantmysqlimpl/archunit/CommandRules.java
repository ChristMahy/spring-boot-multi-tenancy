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
public class CommandRules {
    private static final String PACKAGE = "be.cmahy.multitenantmysqlimpl.application.command..";

//    @ArchTest
//    public static final ArchRule onlyClassesWithCommandAnnotationAreAllowed = classes()
//        .that().resideInAPackage(PACKAGE)
//        .should().beAnnotatedWith(Command.class);

//    @ArchTest
//    public static final ArchRule allClassesShouldEndWithCommandSuffix = classes()
//        .that().resideInAPackage(PACKAGE)
//        .should().haveSimpleNameEndingWith("Command");

//    @ArchTest
//    public static final ArchRule onlyCommandAnnotationIsAllowedInRightPackage = ArchRuleDefinition.noClasses()
//        .that().areAnnotatedWith(Command.class)
//        .should().resideOutsideOfPackage(PACKAGE);
}
