package be.cmahy.multitenantmysql.configthird;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;

public class MultiTenantUser extends User {
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
            PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            List.of(new SimpleGrantedAuthority("USER "))
        );

        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }
}
