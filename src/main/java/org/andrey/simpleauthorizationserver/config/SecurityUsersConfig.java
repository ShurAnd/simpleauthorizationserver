package org.andrey.simpleauthorizationserver.config;

import org.andrey.simpleauthorizationserver.security.JdbcUserDetailsRepository;
import org.andrey.simpleauthorizationserver.security.JdbcUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Класс для конфигурации UserDetailsService и PasswordEncoder для аутентикации пользователей
 */
@Configuration
public class SecurityUsersConfig {

    private JdbcUserDetailsRepository repository;

    @Autowired
    public SecurityUsersConfig(JdbcUserDetailsRepository repository){
        this.repository = repository;
    }

    /**
     * Компонент для оперирования данными пользователей
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new JdbcUserDetailsService(repository);
    }


    /**
     * Компонент для обработки паролей BCrypt хешированием
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(7);
    }
}
