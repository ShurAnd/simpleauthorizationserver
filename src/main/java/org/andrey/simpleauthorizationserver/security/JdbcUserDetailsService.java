package org.andrey.simpleauthorizationserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Сервис для работы с пользователями посредством JDBC
 */
public class JdbcUserDetailsService implements UserDetailsService {

    private JdbcUserDetailsRepository repository;

    public JdbcUserDetailsService(JdbcUserDetailsRepository repository){
        this.repository = repository;
    }

    /**
     * Метод для загрузки пользователя из репозитория по username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserByUsername(username);
    }
}
