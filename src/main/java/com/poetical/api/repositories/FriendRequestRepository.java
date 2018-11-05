package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poetical.api.models.FriendRequest;
import com.poetical.api.models.User;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Page<FriendRequest> findByRecepient(User recipient, Pageable pageable);
    Page<FriendRequest> findByFrom(User from, Pageable pageable);
}