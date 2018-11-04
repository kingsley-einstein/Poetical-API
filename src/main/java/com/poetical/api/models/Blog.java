package com.poetical.api.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "blogs")
public class Blog implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    @Column(nullable = false, name = "title")
    private String title;

    @NotEmpty(message = "Content cannot be left empty")
    @Column(nullable = false, name = "content", columnDefinition = "TEXT", length = 9000)
    private String content;

    @JsonIgnore
    @ManyToOne(optional = false)
    private User author;

    @OneToMany(mappedBy = "blog", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments;

    protected Blog(){}

    public Blog(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }
}