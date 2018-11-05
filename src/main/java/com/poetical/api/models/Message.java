package com.poetical.api.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "messages") 
public class Message implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isread", nullable = false)
    private boolean isRead;

    @OneToMany(mappedBy = "message", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<MessageText> messageTexts;

    @ManyToOne
    private User author;

    @ManyToOne
    private User recepient;

    protected Message(){}

    public Message(boolean isRead, User author, User recepient) {
        this.isRead = isRead;
        this.author = author;
        this.recepient = recepient;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public List<MessageText> getMessageTexts() {
        return messageTexts;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author.getUsername();
    }

    public String getRecepient() {
        return recepient.getUsername();
    }
}