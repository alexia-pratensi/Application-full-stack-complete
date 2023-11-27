package com.openclassrooms.mddapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.openclassrooms.mddapi.security.jwt.AuthEntryPointJwt;
import com.openclassrooms.mddapi.security.jwt.AuthTokenFilter;
import com.openclassrooms.mddapi.security.services.UserDetailsServiceImpl;

/**
 * This class is responsible for the security configuration of the application.
 * It includes the configuration of the authentication manager, password
 * encoder, JWT token filter, and HTTP security.
 * It also provides beans for the JWT token filter, password encoder, and
 * authentication manager.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    /**
     * This method configures the authentication manager builder with the user
     * details service and password encoder.
     *
     * @param auth This is the AuthenticationManagerBuilder to be configured.
     * @throws Exception This exception is thrown if there is an error in the
     *                   configuration.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * This method provides a bean for the JWT token filter.
     *
     * @return AuthTokenFilter This returns a new instance of the JWT token filter.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * This method provides a bean for the password encoder.
     *
     * @return PasswordEncoder This returns a new instance of the
     *         BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method configures the HTTP security of the application.
     * It disables CORS and CSRF, sets the session management policy to stateless,
     * and configures the URL-based authorization.
     * It also adds the JWT token filter before the
     * UsernamePasswordAuthenticationFilter.
     *
     * @param http This is the HttpSecurity to be configured.
     * @throws Exception This exception is thrown if there is an error in the
     *                   configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/users/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * This method provides a bean for the authentication manager.
     *
     * @return AuthenticationManager This returns the authentication manager.
     * @throws Exception This exception is thrown if there is an error in creating
     *                   the bean.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}