package com.yueejia.data;

import com.yueejia.model.Education;
import com.yueejia.model.Experience;
import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExperienceRepository extends CrudRepository<Experience,Integer> {
    List<Experience> findAllByOrderByIdDesc();
}
