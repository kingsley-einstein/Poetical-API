package com.poetical.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import com.poetical.api.models.Comment;
import com.poetical.api.repositories.CommentRepository;
import com.poetical.api.repositories.UserRepository;
import com.poetical.api.repositories.BlogRepository;
import com.poetical.api.repositories.PoemRepository;

import java.util.Map;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private PoemRepository poemRepo;

    @PostMapping(value = "/add_comment_to_poem")
    public void addCommentToPoem(@RequestParam("author_id") Long author_id, @RequestParam("poem_id") Long poem_id, @RequestBody Map<String, String> body) {
        Comment comment = new Comment(userRepo.findById(author_id).get(), poemRepo.findById(poem_id).get(), body.get("content"));

        commentRepo.save(comment);
    }

    @PostMapping(value = "/add_comment_to_blog")
    public void addCommentToBlog(@RequestParam("author_id") Long author_id, @RequestParam("blog_id") Long blog_id, @RequestBody Map<String, String> body) {
        Comment comment = new Comment(userRepo.findById(author_id).get(), blogRepo.findById(blog_id).get(), body.get("content"));

        commentRepo.save(comment);
    }

    @PutMapping(value = "/update/{id}")
    public void updateComment(@PathVariable("id") Long id, @RequestBody Map<String, String> body) {
        Comment comment = commentRepo.findById(id).get();
        comment.setContent(body.get("content"));

        commentRepo.save(comment);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentRepo.deleteById(id);
    }
}