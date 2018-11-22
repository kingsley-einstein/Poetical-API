package com.poetical.api.models;

import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "friendrequests")
public class FriendRequest implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User recepient;

    @ManyToOne
    private User from;

    protected FriendRequest(){}

    public FriendRequest(User recepient, User from) {
        this.recepient = recepient;
        this.from = from;
    }

    public Long getId() {
        return id;
    }

    public String getFrom() {
        return from.getUsername();
    }

    public String getTo() {
        return recepient.getUsername();
    }

    public Long getSenderId() {
        return from.getId();
    }
}