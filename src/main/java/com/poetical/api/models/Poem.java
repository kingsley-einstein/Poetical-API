package com.poetical.api.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "poems")
public class Poem implements java.io.Serializable {

    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "Title cannot be left empty")
    @Column(name = "title")
    private String title;

    @NotNull
    @NotEmpty(message = "Content cannot be left empty")
    @Column(name = "content", columnDefinition = "TEXT", length = 6000)
    private String content;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private User author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @OneToMany(mappedBy = "poemLiked")
    private List<Like> likes;

    protected Poem(){}

    public Poem(String title, String content, User author, Date created) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.created = created;
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

    public String getDateCreated() {
        return format.format(created);
    }

    public Integer getLikes() {
        return likes != null ? likes.size() : 0;
    }

}
