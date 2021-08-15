package com.yueejia.data;

import com.yueejia.model.BlogPost;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface PostRepository extends CrudRepository<BlogPost,Integer> {
    BlogPost findById(Long id);
    BlogPost findByZonedDateTime(ZonedDateTime zonedDateTime);
    BlogPost findByUserId(Long userId);
    List<BlogPost> findAll();
    List<BlogPost> findAllByOrderByIdDesc();
    void deleteById(Long id);

}
