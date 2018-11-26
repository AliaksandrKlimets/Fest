package com.training.iba.controller;

import com.training.iba.entity.Comment;
import com.training.iba.entity.User;
import com.training.iba.repository.CommentRepository;
import com.training.iba.repository.UserRepository;
import com.training.iba.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/{festId}/add-comment/{userId}")
    public Comment addComment(@PathVariable("festId") long festId, @PathVariable("userId") long userId, @RequestBody String text){
        return festivalService.addComment(festId, userId, text);
    }

    @DeleteMapping("/delete/comment/{id}")
    public void deleteComment(@PathVariable("id") Comment comment){
        festivalService.deleteComment(comment);
    }

    @PostMapping("/{commentId}/like/{userId}")
    public void like(@PathVariable("commentId") long commentId, @PathVariable("userId") long userId){
        User user = userRepository.findById(userId);
        Comment comment = commentRepository.findById(commentId);
        comment.getUsersLikes().add(user);
        commentRepository.save(comment);
    }

    @PostMapping("/{commentId}/unlike/{userId}")
    public void unlike(@PathVariable("commentId") long commentId, @PathVariable("userId") long userId){
        User user = userRepository.findById(userId);
        Comment comment = commentRepository.findById(commentId);
        comment.getUsersLikes().remove(user);
        commentRepository.save(comment);
    }
}
