package com.example.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;
    @Autowired
    AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/admin/**").hasRole("ADMIN").
                        requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/signin/**").permitAll().anyRequest().authenticated());
//        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("password1")).roles("USER").build();

        UserDetails user2 = User.withUsername("user2").password(passwordEncoder().encode("password2")).roles("USER").build();

        UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN").build();

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        if (!manager.userExists("user1")) {
            manager.createUser(user1);
        }
        if (!manager.userExists("user2")) {
            manager.createUser(user2);
        }
        if (manager.userExists("admin")) {
            manager.deleteUser("admin");
        }

        manager.createUser(admin);

        return manager;


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

}
