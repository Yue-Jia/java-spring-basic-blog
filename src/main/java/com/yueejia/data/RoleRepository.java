package com.yueejia.data;

import com.yueejia.model.Role;
import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface RoleRepository extends CrudRepository<Role,Integer> {
    Role findByRoleName(String rolename);
}
