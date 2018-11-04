package com.poetical.api.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.poetical.api.models.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    
}