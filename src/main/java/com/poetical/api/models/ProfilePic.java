package com.poetical.api.models;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pictures")
public class ProfilePic implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "data", columnDefinition = "mediumBlob")
    private byte[] data;

    @Column(name = "mimetype", nullable = false)
    private String mimeType;

    @JsonIgnore
    @OneToOne(mappedBy = "profilepic")
    private User owner;

    protected ProfilePic(){}

    public ProfilePic(byte[] data, String mimeType, User owner) {
        this.data = data;
        this.mimeType = mimeType;
        this.owner = owner;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public User getOwner() {
        return owner;
    }
}