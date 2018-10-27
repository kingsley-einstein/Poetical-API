package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poetical.api.models.ProfilePic;

@Repository
public interface ProfilePicRepository extends JpaRepository<ProfilePic, Long> {

}