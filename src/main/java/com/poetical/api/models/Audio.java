package com.poetical.api.models;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.SimpleDateFormat;

@Entity
@Table(name = "audios")
public class Audio implements java.io.Serializable {

    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Lob
    @NotEmpty(message = "Binary data is required")
    @Column(name = "data", columnDefinition = "mediumBlob", nullable = false)
    private byte[] data;

    @Column(name = "mimetype")
    private String mimeType;

    @JsonIgnore
    @ManyToOne
    private User author;

    protected Audio(){}

    public Audio(Date created, byte[] data, String mimeType, User author) {
        this.created = created;
        this.data =  data;
        this.mimeType = mimeType;
        this.author = author;
    }

    public String getMimeType() {
        return mimeType;
    }

    public byte[] getData() {
        return data;
    }

    public String getCreatedDate() {
        return format.format(created);
    }

    public User getAuthor() {
        return author;
    }

}