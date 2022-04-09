package be.cmahy.multitenantmysqlimpl.config;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component("datasourceBasedMultitenantConnectionProvider")
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);

    private static final long serialVersionUID = 1L;

    private final DataSourceProperties dataSourceProperties;

    public DataSourceBasedMultiTenantConnectionProviderImpl(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        LOG.info("Select any dataSource...");

        return (DataSource) this.dataSourceProperties.getDataSources().values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        LOG.info("Try to get dataSource from {}", tenantIdentifier);

        //check again if tenant exist in map after rescan master_db, if not, throw UsernameNotFoundException
        if (!this.dataSourceProperties.getDataSources().containsKey(tenantIdentifier)) {
            LOG.warn("Trying to get tenant:" + tenantIdentifier + " which was not found in master db after rescan");
            throw new UsernameNotFoundException(String.format("Tenant not found after rescan, " + " tenant=%s", tenantIdentifier));
        }
        return (DataSource) this.dataSourceProperties.getDataSources().get(tenantIdentifier);
    }
}
