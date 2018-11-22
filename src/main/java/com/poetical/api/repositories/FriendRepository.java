package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.poetical.api.models.Friend;
import com.poetical.api.models.User;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Page<Friend> findByUser(User user, Pageable pageable);
}