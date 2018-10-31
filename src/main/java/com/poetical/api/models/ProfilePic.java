package com.poetical.api.models;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
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
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    protected ProfilePic(){}

    public ProfilePic(byte[] data, String mimeType) {
        this.data = data;
        this.mimeType = mimeType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public Long getId() {
        return id;
    }
}