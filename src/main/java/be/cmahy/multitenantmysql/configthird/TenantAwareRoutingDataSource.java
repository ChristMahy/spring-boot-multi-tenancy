package be.cmahy.multitenantmysql.configthird;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicBoolean;

public class TenantAwareRoutingDataSource extends AbstractRoutingDataSource {
    private final static Logger log = LoggerFactory.getLogger(TenantAwareRoutingDataSource.class);

    private final AtomicBoolean initialized = new AtomicBoolean();

    @Override
    protected DataSource determineTargetDataSource() {
        if (initialized.compareAndSet(false, true)) {
            this.afterPropertiesSet();
        }

        return super.determineTargetDataSource();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("Get current lookup key");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Authentication get");

        if (authentication != null && authentication.getPrincipal() instanceof MultiTenantUser) {
            log.info("Authentication is a {}", MultiTenantUser.class.getSimpleName());

            MultiTenantUser user = (MultiTenantUser) authentication.getPrincipal();

            log.info("Authentication user tenant id: {}", user.getTenantId());

            return user.getTenantId();
        }

        log.info("Authentication is not a {}, return a null", MultiTenantUser.class.getSimpleName());

        return "ds1";
    }
}
