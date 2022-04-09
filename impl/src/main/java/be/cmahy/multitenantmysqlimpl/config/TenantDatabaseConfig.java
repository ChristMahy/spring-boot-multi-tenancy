package be.cmahy.multitenantmysqlimpl.config;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {
        "be.cmahy"
    },
    entityManagerFactoryRef = "tenantEntityManagerFactory",
    transactionManagerRef = "tenantTransactionManager"
)
public class TenantDatabaseConfig {
    @Bean(name = "tenantJpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "tenantTransactionManager")
    public JpaTransactionManager transactionManager(
        @Qualifier("tenantEntityManagerFactory") EntityManagerFactory tenantEntityManager
    ) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(tenantEntityManager);
        return transactionManager;
    }

    /**
     * The current tenant identifier resolver
     *
     * @return
     */
    @Bean(name = "currentTenantIdentifierResolver")
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new CurrentTenantIdentifierResolverImpl();
    }

    /**
     * Creates the entity manager factory bean which is required to access the
     * JPA functionalities provided by the JPA persistence provider, i.e.
     * Hibernate in this case.
     *
     * @param connectionProvider
     * @param tenantResolver
     * @return
     */
    @Bean(name = "tenantEntityManagerFactory")
    @ConditionalOnBean(name = "datasourceBasedMultitenantConnectionProvider")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier("tenantJpaVendorAdapter")
            JpaVendorAdapter jpaVendor,
        @Qualifier("datasourceBasedMultitenantConnectionProvider")
            MultiTenantConnectionProvider connectionProvider,
        @Qualifier("currentTenantIdentifierResolver")
            CurrentTenantIdentifierResolver tenantResolver
    ) {
        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
        //All tenant related entities, repositories and service classes must be scanned
        emfBean.setPackagesToScan("be.cmahy.multitenantmysqlimpl");
        emfBean.setJpaVendorAdapter(jpaVendor);
        emfBean.setPersistenceUnitName("tenantdb-persistence-unit");
        Map<String, Object> properties = new HashMap<>();
        properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.FORMAT_SQL, true);
        properties.put(Environment.HBM2DDL_AUTO, "none");
        emfBean.setJpaPropertyMap(properties);
        return emfBean;
    }
}
