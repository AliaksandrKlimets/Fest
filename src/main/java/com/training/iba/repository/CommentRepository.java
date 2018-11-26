package com.training.iba.repository;

import com.training.iba.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Comment findById(long id);
}
