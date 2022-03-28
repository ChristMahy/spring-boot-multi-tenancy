//package be.cmahy.multitenantmysql.oldconfig;
//
//import be.cmahy.multitenantmysql.configthird.MultiTenantUser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import javax.sql.DataSource;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//public class MultitenantDataSource extends AbstractRoutingDataSource {
//    private final static Logger log = LoggerFactory.getLogger(MultitenantDataSource.class);
//
//    private final AtomicBoolean initialized = new AtomicBoolean();
//
//    @Override
//    protected DataSource determineTargetDataSource() {
//        if (initialized.compareAndSet(false, true)) {
//            this.afterPropertiesSet();
//        }
//
//        return super.determineTargetDataSource();
//    }
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//        log.debug("Get current lookup key");
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        log.debug("Authentication get");
//
//        if (authentication != null && authentication.getPrincipal() instanceof MultiTenantUser) {
//            log.debug("Authentication is a {}", MultiTenantUser.class.getSimpleName());
//
//            MultiTenantUser user = (MultiTenantUser) authentication.getPrincipal();
//
//            log.debug("Authentication user tenant id: {}", user.getTenantId());
//
//            return user.getTenantId();
//        }
//
//        log.debug("Authentication is not a {}, return a null", MultiTenantUser.class.getSimpleName());
//
//        return null;
//    }
//}
