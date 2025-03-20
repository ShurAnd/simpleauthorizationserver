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
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.UUID;

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

    @Bean
    public RegisteredClientRepository registeredClientRepository(){
        RegisteredClient registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("custom client")
                .clientSecret("custom client secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:9090/authorized")
                .scope(OidcScopes.OPENID)
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }


    /**
     * Компонент для обработки паролей BCrypt хешированием
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(7);
    }
}
