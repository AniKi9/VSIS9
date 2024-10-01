package com.prc.vsis9.repository;

import com.prc.vsis9.data.User;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
