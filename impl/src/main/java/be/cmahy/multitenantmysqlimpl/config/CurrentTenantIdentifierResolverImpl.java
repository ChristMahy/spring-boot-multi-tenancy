package be.cmahy.multitenantmysqlimpl.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    private final static Logger log = LoggerFactory.getLogger(CurrentTenantIdentifierResolverImpl.class);

    private static final String DEFAULT_TENANT_ID = "";

    @Override
    public String resolveCurrentTenantIdentifier() {
        log.info("Get current lookup key");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Authentication get");

        if (authentication != null && authentication.getPrincipal() instanceof MultiTenantUser) {
            log.info("Authentication is a {}", MultiTenantUser.class.getSimpleName());

            MultiTenantUser user = (MultiTenantUser) authentication.getPrincipal();

            log.info("Authentication user tenant id: {}", user.getTenantId());

            return user.getTenantId();
        }

        log.info(
            "Authentication is a {}, not {}, return {}",
            authentication == null ? "null" : authentication.getPrincipal().getClass().getSimpleName(),
            MultiTenantUser.class.getSimpleName(),
            DEFAULT_TENANT_ID
        );

        return DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
