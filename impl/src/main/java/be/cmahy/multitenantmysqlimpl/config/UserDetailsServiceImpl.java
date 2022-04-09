package be.cmahy.multitenantmysqlimpl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final Map<String, MultiTenantUser> users = new HashMap<>();

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        initializeUsers(passwordEncoder);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);

        log.info("User ({}): {} - {}", user.getClass().getSimpleName(), user.getUsername(), user.getPassword());

        return user;
    }

    private void initializeUsers(PasswordEncoder passwordEncoder) {
        MultiTenantUser user = new MultiTenantUser(
            "donald",
            passwordEncoder.encode("pw"),
            true,
            true,
            true,
            true,
            "ds1"
        );

        users.put(user.getUsername(), user);

        user = new MultiTenantUser(
            "ralph",
            passwordEncoder.encode("pw"),
            true,
            true,
            true,
            true,
            "ds2"
        );

        users.put(user.getUsername(), user);
    }
}
