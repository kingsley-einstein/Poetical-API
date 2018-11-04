package com.poetical.api.models;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Blog blog;

    @ManyToOne(cascade = CascadeType.ALL)
    private Poem poem;

    protected Comment(){}

    public Comment(User author, Blog blog) {
        this.author = author;
        this.blog = blog;
    }

    public Comment(User author, Poem poem) {
        this.author = author;
        this.poem = poem;
    }

    public User getAuthor() {
        return author;
    }
}