package be.cmahy.multitenantmysql.configthird;

import be.cmahy.multitenantmysql.config.CurrentTenantIdentifierResolverImpl;
import be.cmahy.multitenantmysql.config.MultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
public class DataSourceConfiguration {

    private final DataSourceProperties dataSourceProperties;

    public DataSourceConfiguration(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    @Primary
    public DataSource getDataSource() {
        TenantAwareRoutingDataSource tenantAwareRoutingDataSource = new TenantAwareRoutingDataSource();
        tenantAwareRoutingDataSource.setTargetDataSources(dataSourceProperties.getDataSources());
        tenantAwareRoutingDataSource.afterPropertiesSet();
        return tenantAwareRoutingDataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");

        emFactory.setJpaVendorAdapter(vendorAdapter);
        emFactory.setPackagesToScan("be.cmahy.multitenantmysql.entity");

        Map<String, Object> jpaProps = new HashMap<>();

//        jpaProps.setProperty(DIALECT, "org.hibernate.dialect.MySQL8Dialect");
//        jpaProps.setProperty(DRIVER, "com.mysql.cj.jdbc.Driver");
//        jpaProps.setProperty(HBM2DDL_AUTO, "validate");
//        jpaProps.setProperty(SHOW_SQL, "false");
//        jpaProps.setProperty(FORMAT_SQL, "false");
//        jpaProps.setProperty(GENERATE_STATISTICS, "false");

        jpaProps.put(MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider());
        jpaProps.put(MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver());
        jpaProps.put(MULTI_TENANT, "DATABASE");

        emFactory.setJpaPropertyMap(jpaProps);

        return emFactory;
    }

    @Bean
    public MultiTenantConnectionProviderImpl multiTenantConnectionProvider() {
        return new MultiTenantConnectionProviderImpl();
    }

    @Bean
    public CurrentTenantIdentifierResolverImpl tenantResolver() {
        return new CurrentTenantIdentifierResolverImpl();
    }
}
