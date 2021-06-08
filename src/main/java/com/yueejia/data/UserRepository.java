package com.yueejia.data;

import com.yueejia.model.Role;
import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPassword(String password);
    User findByRoleContaining(Role role);
}
