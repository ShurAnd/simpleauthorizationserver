package org.andrey.simpleauthorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Класс для конфигурации настроек Spring Security
 */
@Configuration
public class SecurityConfig {

    /**
     * Настройки Spring Security
     */
    @Bean
    @Order(1)
    public SecurityFilterChain asFilterChain(HttpSecurity http) throws Exception{
        // Включаем протокол oidc
        http
                .with(OAuth2AuthorizationServerConfigurer.authorizationServer(),
                c -> c.oidc(Customizer.withDefaults()));

        // Указываем путь к странице ввода логина пароля
        http
                .exceptionHandling(e -> e.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")
                ));

        return http.build();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        // Включаем аутентикацию по логин паролю
        http
                .formLogin(Customizer.withDefaults());

        // Указываем что все endpoints будут требовать аутентикации
        http
                .authorizeHttpRequests(c -> c
                .anyRequest().authenticated());

        return http.build();
    }
}
