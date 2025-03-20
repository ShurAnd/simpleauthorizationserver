package org.andrey.simpleauthorizationserver.security;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JdbcUserDetailsRepository extends CrudRepository<User, Long> {
    @Query("SELECT * FROM users u WHERE u.username = :username")
    User findUserByUsername(String username);
}
