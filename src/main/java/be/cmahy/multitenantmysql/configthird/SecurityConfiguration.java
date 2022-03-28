package be.cmahy.multitenantmysql.configthird;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final static Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        log.info("Load users security");

        User donald = createUser("donald", "ds1");
        User ralph = createUser("ralph", "ds2");

//        manager.createUser(donald);
//        manager.createUser(ralph);

//        return manager;

        Map<String, User> users = Stream.of(donald, ralph)
            .collect(Collectors.toMap(User::getUsername, u -> u));

        log.info("Users security maps");

        return username -> {
            User user = users.getOrDefault(username, null);

            if (user == null) {
                log.info("User security not found !!!");

                throw new UsernameNotFoundException("Couldn't find " + username + "!");
            }

            log.info("User security {}", user.getUsername());

            return user;
        };
    }

    private static User createUser(String name, String tenantId) {
        log.info("Create user security {} - {}", name, tenantId);

        return new MultiTenantUser(
            name,
            PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("pw"),
            true,
            true,
            true,
            true,
            tenantId
        );
    }
}
