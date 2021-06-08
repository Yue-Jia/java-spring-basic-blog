package com.yueejia.data;

import com.yueejia.model.Resume;
import org.springframework.data.repository.CrudRepository;

public interface ResumeRepository extends CrudRepository<Resume,Integer> {
    Resume findFirstByOrderByIdAsc();
}
