package com.yueejia.data;

import com.yueejia.model.BlogPost;
import com.yueejia.model.Comment;
import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
    void deleteById(Long id);
    void deleteByBlogPost(BlogPost blogPost);
    List<Comment> findByBlogPost(BlogPost blogPost);
    Comment findById(Long id);
}
