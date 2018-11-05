package com.poetical.api.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "texts")
public class MessageText implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Message text cannot be empty")
    @Column(nullable = false, name = "text", columnDefinition = "TEXT")
    private String text;

    @JsonIgnore
    @ManyToOne
    private Message message;

    @ManyToOne
    private User author;

    protected MessageText(){}

    public MessageText(String text, Message message, User author) {
        this.text = text;
        this.message = message;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public Message getMessage() {
        return message;
    }

    public String getAuthor() {
        return author.getUsername();
    }

}