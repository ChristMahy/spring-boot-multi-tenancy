package be.cmahy.multitenantmysql.config;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import javax.sql.DataSource;

public class MultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private final static Logger log = LoggerFactory.getLogger(MultiTenantConnectionProviderImpl.class);

    private static final Long serialVersionUID = 1L;

    @Autowired
    private DataSource defaultDataSource;
    @Autowired
    private DataSourceLookup dataSourceLookup;

    @Override
    protected DataSource selectAnyDataSource() {
        log.trace("Select any dataSource: {}", defaultDataSource);

        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantId) {
        DataSource dataSource = dataSourceLookup.getDataSource(tenantId);

        log.trace("Select dataSource from {}: {}", tenantId, dataSource);

        return dataSource;
    }
}
