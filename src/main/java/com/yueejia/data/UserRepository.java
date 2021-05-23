package com.yueejia.data;

import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findById(Long id);
    User findByName(String name);
}
