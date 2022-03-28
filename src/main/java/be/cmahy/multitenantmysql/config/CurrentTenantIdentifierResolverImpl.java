package be.cmahy.multitenantmysql.config;

import be.cmahy.multitenantmysql.configthird.MultiTenantUser;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    private final static Logger log = LoggerFactory.getLogger(CurrentTenantIdentifierResolverImpl.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = resolveTenantIdBySpringSecurity();

        log.debug("Tenant resolved: {}", tenantId);

        return tenantId;
    }

    public String resolveTenantIdBySpringSecurity() {
        log.debug("Get current lookup key");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.debug("Authentication get");

        if (authentication != null && authentication.getPrincipal() instanceof MultiTenantUser) {
            log.debug("Authentication is a {}", MultiTenantUser.class.getSimpleName());

            MultiTenantUser user = (MultiTenantUser) authentication.getPrincipal();

            log.debug("Authentication user tenant id: {}", user.getTenantId());

            return user.getTenantId().toString();
        }

        log.debug("Authentication is not a {}, return a null", MultiTenantUser.class.getSimpleName());

        return null;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
