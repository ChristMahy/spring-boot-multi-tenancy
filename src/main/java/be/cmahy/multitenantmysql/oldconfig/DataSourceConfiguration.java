//package be.cmahy.multitenantmysql.oldconfig;
//
//import be.cmahy.multitenantmysql.config.CurrentTenantIdentifierResolverImpl;
//import be.cmahy.multitenantmysql.config.MultiTenantConnectionProviderImpl;
//import org.hibernate.cfg.AvailableSettings;
//import org.hibernate.jpa.HibernatePersistenceProvider;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//import java.util.stream.Collectors;
//
//import static org.hibernate.cfg.AvailableSettings.*;
//
//@Configuration
//@EnableJpaRepositories(basePackages = {"be.cmahy.multitenantmysql.repository"})
//public class DataSourceConfiguration {
//    @Bean
//    @Primary
//    DataSource multitenantDataSource(Map<String, DataSource> dataSources) {
//        String prefix = "ds";
//
//        Map<Object, Object> map = dataSources
//            .entrySet()
//            .stream()
//            .filter(e -> e.getKey().startsWith(prefix))
//            .collect(Collectors.toMap(
//                e -> Integer.parseInt(e.getKey().substring(prefix.length())),
//                Map.Entry::getValue
//            ));
//
//        MultitenantDataSource mds = new MultitenantDataSource();
//
//        mds.setTargetDataSources(map);
//
//        return mds;
//    }
//
//    @Bean
//    DataSource ds1() {
//        return dataSource(4321);
//    }
//
//    @Bean
//    DataSource ds2() {
//        return dataSource(4322);
//    }
//
//    private static DataSource dataSource(int port) {
//        DataSourceProperties dsp = new DataSourceProperties();
//
//        dsp.setUsername("christophe");
//        dsp.setPassword("pw1234");
//        dsp.setUrl("jdbc:mysql://localhost:" + port + "/christophe");
//
//        return dsp
//            .initializeDataSourceBuilder()
//            .driverClassName("com.mysql.cj.jdbc.Driver")
//            .build();
//    }
//
//    @Bean
//    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
//
//        emFactory.setJpaVendorAdapter(vendorAdapter);
//        emFactory.setPackagesToScan("be.cmahy.multitenantmysql.entity");
//
//        Map<String, Object> jpaProps = new HashMap<>();
//
////        jpaProps.setProperty(DIALECT, "org.hibernate.dialect.MySQL8Dialect");
////        jpaProps.setProperty(DRIVER, "com.mysql.cj.jdbc.Driver");
////        jpaProps.setProperty(HBM2DDL_AUTO, "validate");
////        jpaProps.setProperty(SHOW_SQL, "false");
////        jpaProps.setProperty(FORMAT_SQL, "false");
////        jpaProps.setProperty(GENERATE_STATISTICS, "false");
//
//        jpaProps.put(MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider());
//        jpaProps.put(MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver());
//        jpaProps.put(MULTI_TENANT, "DATABASE");
//
//        emFactory.setJpaPropertyMap(jpaProps);
//
//        return emFactory;
//    }
//
//    @Bean
//    public MultiTenantConnectionProviderImpl multiTenantConnectionProvider() {
//        return new MultiTenantConnectionProviderImpl();
//    }
//
//    @Bean
//    public CurrentTenantIdentifierResolverImpl tenantResolver() {
//        return new CurrentTenantIdentifierResolverImpl();
//    }
//
//    /*@Bean
//    JpaTransactionManager transactionManager(
//        EntityManagerFactory entityManagerFactory
//    ) {
//        JpaTransactionManager jpaTransMan = new JpaTransactionManager();
//
//        jpaTransMan.setEntityManagerFactory(entityManagerFactory);
//
//        return jpaTransMan;
//    }*/
//}
