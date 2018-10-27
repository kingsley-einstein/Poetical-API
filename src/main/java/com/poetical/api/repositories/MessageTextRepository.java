package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poetical.api.models.Message;
import com.poetical.api.models.MessageText;

@Repository
public interface MessageTextRepository extends JpaRepository<MessageText, Long> {

    Page<MessageText> findByMessage(Message message, Pageable pageable);
}