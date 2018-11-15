package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poetical.api.models.Audio;
import com.poetical.api.models.User;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Long> {
    Page<Audio> findAllByAuthor(User author, Pageable pageable);
}