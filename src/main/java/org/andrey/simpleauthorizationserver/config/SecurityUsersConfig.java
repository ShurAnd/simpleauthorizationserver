package org.andrey.simpleauthorizationserver.config;

import org.andrey.simpleauthorizationserver.security.JdbcUserDetailsService;
import org.andrey.simpleauthorizationserver.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Класс для конфигурации UserDetailsService и PasswordEncoder для аутентикации пользователей
 */
@Configuration
public class SecurityUsersConfig {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SecurityUsersConfig(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Компонент для оперирования данными пользователей
     */
    @Bean
    public UserDetailsService userDetailsService(){
        JdbcUserDetailsService userDetailsService = new JdbcUserDetailsService(jdbcTemplate);

        try{
            userDetailsService.loadUserByUsername("andrey");
        } catch (UsernameNotFoundException e) {
            User andreyUser = new User();
            andreyUser.setUsername("andrey");
            andreyUser.setPassword(passwordEncoder().encode("andrey"));
            userDetailsService.saveNewUser(andreyUser);
        }

        return userDetailsService;
    }


    /**
     * Компонент для обработки паролей BCrypt хешированием
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(7);
    }
}
