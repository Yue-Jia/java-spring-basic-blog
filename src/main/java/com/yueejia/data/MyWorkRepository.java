package com.yueejia.data;

import com.yueejia.model.MyWork;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MyWorkRepository extends CrudRepository<MyWork,Integer> {
    MyWork findById(Long id);
    List<MyWork> findAll();
    void deleteById(Long id);
}
