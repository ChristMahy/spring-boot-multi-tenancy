package be.cmahy.multitenantmysqlimpl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class MultiTenantUser extends User {
    private final static Logger log = LoggerFactory.getLogger(MultiTenantUser.class);

    private final String tenantId;

    public MultiTenantUser(
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
        String tenantId
    ) {
        super(
            username,
            password,
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            List.of(new SimpleGrantedAuthority("USER"))
        );

        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }
}
