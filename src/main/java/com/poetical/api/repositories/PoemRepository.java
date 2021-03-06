package com.poetical.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poetical.api.models.Poem;
import com.poetical.api.models.User;

import java.util.Optional;

@Repository
public interface PoemRepository extends JpaRepository<Poem, Long> {
    
    public Page<Poem> findByAuthor(User author, Pageable pageable);
    public Page<Poem> findByTitleContaining(String title, Pageable pageable);
    public Optional<Poem> findByTitle(String title);
}