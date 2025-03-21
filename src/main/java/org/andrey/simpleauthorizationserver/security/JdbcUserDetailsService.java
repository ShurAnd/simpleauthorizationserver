package org.andrey.simpleauthorizationserver.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с пользователями посредством JDBC
 */
public class JdbcUserDetailsService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDetailsService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод для загрузки пользователя из репозитория по username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String userSql = "Select username, password from users where username = ?";
        String authoritySql = "Select authority from authorities where username = ?";

        List<User> result = jdbcTemplate.query(userSql, (r, i) -> {
           String uname = r.getString("username");
           String upass = r.getString("password");

           User user = new User();
           user.setUsername(uname);
           user.setPassword(upass);

           return user;
        }, username);

        if (result.isEmpty()){
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        User u = result.getFirst();

        List<String> authorities = new ArrayList<>();
        authorities = jdbcTemplate.query(authoritySql,
                (r, i) -> r.getString("authority"),
                username);

        u.setAuthorities(authorities);

        return u;
    }

    /**
     * Метод для сохранения нового пользователя в БД
     */
    public void saveNewUser(User user){
        String sqlUsers = "Insert into users (username, password) values (?, ?)";
        String sqlAuthorities = "Insert into authorities (username, authority) values (?, ?)";
        jdbcTemplate.update(sqlUsers, user.getUsername(), user.getPassword());
        String sqlGetUserId = "Select id from users where username = ?";
        Long id = jdbcTemplate.queryForObject(sqlGetUserId, (r, i) -> r.getLong("id"), user.getUsername());
        jdbcTemplate.update(sqlAuthorities, id, user.getPassword());
    }
}
