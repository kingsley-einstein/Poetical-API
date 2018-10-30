package com.poetical.api.models;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "poemlikes")
public class Like implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User likedBy;

    @JsonIgnore
    @ManyToOne
    private Poem poemLiked;

    protected Like(){}

    public Like(User likedBy, Poem poemLiked) {
        this.likedBy = likedBy;
        this.poemLiked = poemLiked;
    }
}