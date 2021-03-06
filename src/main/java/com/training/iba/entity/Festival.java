package com.training.iba.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Table(name = "festival")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String festivalName;
    private boolean isAvailable = true;
    private String place;
    private String festInfo;
    private Date festDate;
    private String festTime;
    private double cost;
    private String festPhoto;
    @Transient
    private long[] artistsIdList;
    @Transient
    private boolean attention;
    @Transient
    private boolean ended = false;
    public long[] getArtistsIdList() {
        return artistsIdList;
    }

    public void setArtistsIdList(long[] artistsIdList) {
        this.artistsIdList = artistsIdList;
    }

    @ElementCollection(targetClass = Genres.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "genre", joinColumns = @JoinColumn(name = "entity_id"))
    @Enumerated(EnumType.STRING)
    private Set<Genres> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "fest_participants",
            joinColumns = {@JoinColumn(name = "fest_id")},
            inverseJoinColumns = {@JoinColumn(name = "participant_id")}
    )
    private Set<User> participants = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "fest_artists",
            joinColumns = {@JoinColumn(name = "fest_id")},
            inverseJoinColumns = {@JoinColumn(name = "artist_id")}
    )
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "fest_comments",
            joinColumns = {@JoinColumn(name = "fest_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id")}
    )
    private List<Comment> comments = new ArrayList<>();

    public Festival() {
    }

    public Festival(boolean isAvailable, String place, String festInfo,
                    Date festDate, String time, double cost, String festPhoto, Set<Genres> genres, String festivalName) {
        this.isAvailable = isAvailable;
        this.place = place;
        this.festInfo = festInfo;
        this.festDate = festDate;
        this.cost = cost;
        this.festPhoto = festPhoto;
        this.genres = genres;
        this.festivalName = festivalName;
        this.festTime = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFestInfo() {
        return festInfo;
    }

    public void setFestInfo(String festInfo) {
        this.festInfo = festInfo;
    }

    public Date getFestDate() {
        return festDate;
    }

    public void setFestDate(Date festDate) {
        this.festDate = festDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getFestPhoto() {
        return festPhoto;
    }

    public void setFestPhoto(String festPhoto) {
        this.festPhoto = festPhoto;
    }

    public Set<Genres> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public String getFestTime() {
        return festTime;
    }

    public void setFestTime(String festTime) {
        this.festTime = festTime;
    }

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Festival festival = (Festival) o;
        return Objects.equals(id, festival.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Festival{" +
                "id=" + id +
                ", festivalName='" + festivalName + '\'' +
                ", isAvailable=" + isAvailable +
                ", place='" + place + '\'' +
                ", festInfo='" + festInfo + '\'' +
                ", festDate=" + festDate +
                ", cost=" + cost +
                ", festPhoto='" + festPhoto + '\'' +
                ", genres=" + genres +
                ", participants=" + participants +
                ", artists=" + artists +
                '}';
    }
}
