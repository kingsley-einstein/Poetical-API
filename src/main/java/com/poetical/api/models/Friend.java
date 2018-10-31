package com.poetical.api.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "users_friends")
public class Friend implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", length = 20, columnDefinition = "BIGINT")
    private User user;

    @Column(name = "friend_id", length = 20, columnDefinition = "BIGINT")
    private User friends;
}