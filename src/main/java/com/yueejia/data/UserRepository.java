package com.yueejia.data;

import com.yueejia.model.Post;
import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
