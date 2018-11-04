package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poetical.api.models.Comment;
import com.poetical.api.models.Blog;
import com.poetical.api.models.Poem;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPoem(Poem poem, Pageable pageable);
    Page<Comment> findByBlog(Blog blog, Pageable pageable);
}