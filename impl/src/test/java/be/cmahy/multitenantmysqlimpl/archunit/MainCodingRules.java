package be.cmahy.multitenantmysqlimpl.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.slf4j.Logger;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

@AnalyzeClasses(
    packages = {"be.cmahy.multitenantmysqlimpl"},
    importOptions = {
        ImportOption.DoNotIncludeTests.class,
        ImportOption.DoNotIncludeArchives.class,
        ImportOption.DoNotIncludeJars.class
    }
)
public class MainCodingRules {
//    @ArchTest
//    public static final ArchRule avoidIOStandardStreamUse = noClasses()
//        .that().doNotHaveSimpleName(PrintMessageService.class.simpleName)
//        .should(ACCESS_STANDARD_STREAMS);

    @ArchTest
    public static final ArchRule avoidJavaUtilLoggingUse = noClasses()
        .that().resideInAPackage("be.cmahy.multitenantmysqlimpl")
        .should(USE_JAVA_UTIL_LOGGING);

    @ArchTest
    public static final ArchRule loggerAsPrivateStaticFinal = fields()
        .that().haveRawType(Logger.class)
        .should().bePrivate()
        .andShould().beStatic()
        .andShould().beFinal();

    @ArchTest
    public static final ArchRule avoidJodaTimeUse = NO_CLASSES_SHOULD_USE_JODATIME;

    @ArchTest
    public void avoidStandardStreamAsMethodUse(JavaClasses javaClasses) {
        noClasses()
            .should(ACCESS_STANDARD_STREAMS)
            .check(javaClasses);
    }
}
