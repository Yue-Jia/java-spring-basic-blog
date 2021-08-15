package com.yueejia.data;

import com.yueejia.model.Education;
import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EducationRepository extends CrudRepository<Education,Integer> {
    List<Education> findAllByOrderByIdDesc();
}
