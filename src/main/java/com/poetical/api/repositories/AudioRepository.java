package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poetical.api.models.Audio;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Long> {

}