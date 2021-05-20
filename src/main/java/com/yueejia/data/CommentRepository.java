package com.yueejia.data;

import com.yueejia.model.Comment;
import com.yueejia.model.User;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
}
