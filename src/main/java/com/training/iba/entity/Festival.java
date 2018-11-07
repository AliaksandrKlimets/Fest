package com.training.iba.entity;


import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Table(name = "festival")
@Entity
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean  isAvailable = true;
    private String place;
    private String festInfo;
    private Date festDate;
    private double cost;
    private String festPhoto;

    @ElementCollection(targetClass = Genres.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "festival_genre", joinColumns = @JoinColumn(name = "festival_id"))
    @Enumerated(EnumType.STRING)
    private Set<Genres> genres;
//
//    @ManyToMany
//    @JoinTable(
//            name = "fest_participants",
//            joinColumns = {@JoinColumn(name = "fest_id")},
//            inverseJoinColumns = {@JoinColumn(name = "participant_id")}
//    )
//    private Set<User> participants = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "fest_artists",
//            joinColumns = {@JoinColumn(name = "fest_id")},
//            inverseJoinColumns = {@JoinColumn(name = "artist_id")}
//    )
//    private Set<Artist> artists = new HashSet<>();

    public Festival() {
    }

    public Festival(boolean isAvailable, String place, String festInfo, Date festDate, double cost, String festPhoto, Set<Genres> genres) {
        this.isAvailable = isAvailable;
        this.place = place;
        this.festInfo = festInfo;
        this.festDate = festDate;
        this.cost = cost;
        this.festPhoto = festPhoto;
        this.genres = genres;
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

//    public Set<User> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(Set<User> participants) {
//        this.participants = participants;
//    }
//
//    public Set<Artist> getArtists() {
//        return artists;
//    }
//
//    public void setArtists(Set<Artist> artists) {
//        this.artists = artists;
//    }

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


}
