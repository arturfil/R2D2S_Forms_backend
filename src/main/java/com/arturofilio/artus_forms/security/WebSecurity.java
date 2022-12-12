package com.arturofilio.artus_forms.security;

import com.arturofilio.artus_forms.services.UserService;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public WebSecurity(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .antMatchers(HttpMethod.POST, "/api/users").permitAll()
            .antMatchers(HttpMethod.GET, "/api/polls/**/questions").permitAll()
            .antMatchers(HttpMethod.POST, "/api/polls/reply").permitAll()
                .anyRequest()
                .authenticated();
        http.addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
        return authenticationFilter;
    }
}
