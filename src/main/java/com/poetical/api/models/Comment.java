package com.poetical.api.models;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Blog blog;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Poem poem;

    @NotNull
    @NotEmpty(message = "You cannot enter an empty comment")
    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    protected Comment(){}

    public Comment(User author, Blog blog, String content) {
        this.author = author;
        this.blog = blog;
        this.content = content;
    }

    public Comment(User author, Poem poem, String content) {
        this.author = author;
        this.poem = poem;
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}