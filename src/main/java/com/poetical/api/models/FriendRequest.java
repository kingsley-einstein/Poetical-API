package com.poetical.api.models;

import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "friendrequests")
public class FriendRequest implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User recipient;

    @JsonIgnore
    @ManyToOne
    private User from;

    protected FriendRequest(){}

    public FriendRequest(User recipient, User from) {
        this.recipient = recipient;
        this.from = from;
    }
}