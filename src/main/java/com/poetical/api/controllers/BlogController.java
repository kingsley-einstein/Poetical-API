package com.poetical.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.poetical.api.models.Blog;
import com.poetical.api.repositories.UserRepository;
import com.poetical.api.repositories.BlogRepository;
import com.poetical.api.exceptions.NotFoundException;

import java.util.Map;

@RestController
@RequestMapping(value = "/blogs")
public class BlogController {

    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping(value = "/all/{page}")
    @ResponseBody
    public Page<Blog> getBlogs(@PathVariable("page") Integer page) {
        Page<Blog> blogs = blogRepo.findAll(PageRequest.of(page, 25));

        return blogs;
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public Blog createBlog(@RequestParam("user_id") Long id, @RequestBody Map<String, String> body) {
        return blogRepo.save(new Blog(body.get("title"), body.get("content"), userRepo.findById(id).get()));
    }
}