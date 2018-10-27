package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poetical.api.models.Message;
import com.poetical.api.models.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByAuthor(User author, Pageable pageable);
    Page<Message> findByRecipient(User recipient, Pageable pageable);
}