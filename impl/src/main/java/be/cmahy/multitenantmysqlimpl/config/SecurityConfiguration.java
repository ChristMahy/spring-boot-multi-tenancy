package be.cmahy.multitenantmysqlimpl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final static Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(
        PasswordEncoder passwordEncoder,
        UserDetailsService userDetailsService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .eraseCredentials(false)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
            .authorizeRequests().anyRequest().authenticated()
            .and().httpBasic();
    }
}
