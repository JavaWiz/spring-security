package com.javawiz.security.inmemory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password("{noop}password")
            .roles("USER")
            .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}password")
                .roles("USER", "ADMIN")
                .build();
        InMemoryUserDetailsManager udm = new InMemoryUserDetailsManager();
        udm.createUser(user);
        udm.createUser(admin);
        return udm;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
		.antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
		.and()
		.csrf().disable()
		.formLogin().disable().build();
    }
}