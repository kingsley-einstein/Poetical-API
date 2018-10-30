package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poetical.api.models.ProfilePic;
import com.poetical.api.models.User;

@Repository
public interface ProfilePicRepository extends JpaRepository<ProfilePic, Long> {

    ProfilePic findByOwner(User owner);
}