package com.yueejia.data;

import com.yueejia.model.Experience;
import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

public interface ExperienceRepository extends CrudRepository<Experience,Integer> {
}
