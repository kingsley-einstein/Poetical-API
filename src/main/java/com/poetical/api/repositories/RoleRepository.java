package com.poetical.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poetical.api.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}