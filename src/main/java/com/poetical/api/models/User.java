package com.poetical.api.models;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;


/**
 * This entity is mapped to the database as a table named 'users'.
 * Holds the information for a poet.
 * 
 * @author Kingsley Victor 
 */
@Entity
@Table(name = "users")
public class User implements java.io.Serializable {
    
    @Transient // => Annotate with @transient to exclude from the table
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Transient
    private Integer unreadMessages = 0;

    @Id // => Annotate with @id to indicate the id for the row
    @GeneratedValue(strategy = GenerationType.IDENTITY) // => Use @generatedvalue to indicate an auto_increment field. 
    private Long id;
    
    @NotNull // => Is not nullable
    @NotEmpty(message = "Email cannot be left empty") // => Field cannot be left empty. You can annotate with @NotBlank as an alternative
    @Column(nullable = false, name = "email", unique = true) // => Set column properties. Is not nullable, is named 'email' and no duplicate field is allowed for another row.
    private String email;

    @Column(nullable = false, name = "hash")
    private String salt; // => Salt to generate BCrypt hashsum for password
    
    @NotNull
    @NotEmpty(message = "Password is required")
    @Column(nullable = false, name = "password")
    private String password;
    
    @NotNull
    @NotEmpty(message = "Username is required")
    @Column(nullable = false, name = "username", unique = true)
    private String username;

    @NotNull
    @NotEmpty(message = "Gender is required")
    @Column(nullable = false, name="gender")
    private String gender;

    @Temporal(TemporalType.TIMESTAMP) // => It is conventional to annotate with @temporal to indicate a timestamp. 
    @Column(name = "date_joined", nullable = false)
    private Date joined;

    @Column(name = "online", nullable = false)
    private boolean isLogged;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "owner") // => Indicates a One-To-One relationship
    private ProfilePic profilePic;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true) // => Indicates a One-To-Many relationship and is mapped to a column named 'author' 
    private List<Poem> poems;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Audio> audios;

    @OneToMany(mappedBy = "author")
    private List<Blog> blogs;

    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles;

    @OneToMany(mappedBy = "author")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recepient")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "author")
    private List<MessageText> composedTexts;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "friends")
    private List<User> users = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_friends", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "friend_id")})
    private List<User> friends = new ArrayList<>();

    @OneToMany(mappedBy = "from")
    private List<FriendRequest> sentRequests;

    @OneToMany(mappedBy = "recepient")
    private List<FriendRequest> receivedRequests;

    @OneToMany(mappedBy = "likedBy")
    private List<Like> likesGiven;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;


    @Transient
    private List<GrantedAuthority> grantedAuthorities;

    protected User(){}

    public User(String email, String salt, String username, String gender, Date joined, boolean isLogged) {
        this.email = email;
        this.salt = salt;
        this.username = username;
        this.gender = gender;
        this.joined = joined;
        this.isLogged = isLogged;
    }

    public Long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public char getGender() {
        return gender.trim().charAt(0);
    }

    public void setGrantedAuthoritiesList(List<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public List<GrantedAuthority> getGrantedAuthoritiesList() {
        return grantedAuthorities;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public boolean checkPassword(String password) {
        return password.equals(getPassword());
    }
    
    public void addFriend(User friend) {
        friends.add(friend);
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<FriendRequest> getSentRequests() {
        return sentRequests;
    }

    public List<FriendRequest> getReceivedRequests() {
        return receivedRequests;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public ProfilePic getProfilePic() {
        return profilePic;
    }

    public List<Poem> getPoems() {
        return poems;
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public String getDateJoined() {
        return format.format(joined);
    }

    public Integer getUnreadMessages() {
        if (receivedMessages != null && receivedMessages.size() > 0) {
            receivedMessages
            .stream()
            .forEach((received)-> {
            if (!(received.getIsRead()) && !received.getMessageTexts().get(received.getMessageTexts().size() - 1).getAuthor().equals(getUsername())) {
                unreadMessages++;
            }
        });
        }

        return unreadMessages;
    }

    public Integer getReceivedRequestsSize() {
        return receivedRequests != null ? receivedRequests.size() : 0;
    }

    public List<Comment> getComments() {
        return comments;
    }

} 