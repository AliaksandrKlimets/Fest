package com.training.iba.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comment")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    private Date date;
    private String author;
    private Long authorId;
    @ManyToMany
    @JoinTable(
            name = "comment_likes",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonIgnore
    private Set<User> usersLikes = new HashSet<>();

    @Transient
    private Set<Long> likes = new HashSet<>();

    public Comment(String text, Date date, String author, Long id) {
        this.text = text;
        this.date = date;
        this.author = author;
        this.authorId = id;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Set<User> getUsersLikes() {
        return usersLikes;
    }

    public void setUsersLikes(Set<User> usersLikes) {
        this.usersLikes = usersLikes;
    }

    public Set<Long> getLikes() {
        return likes;
    }

    public void setLikes(Set<Long> likes) {
        this.likes = likes;
    }
}
