package com.training.iba.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(name = "artist")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String artistName;
    private String artistInfo;
    private String photo;
    private String country;

//    @ManyToMany
//    @JoinTable(
//            name = "fest_artists",
//            joinColumns = {@JoinColumn(name = "artist_id")},
//            inverseJoinColumns = {@JoinColumn(name = "fest_id")}
//    )
//    private Set<Festival> festivals = new HashSet<>();

    @ElementCollection(targetClass = Genres.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "genre", joinColumns = @JoinColumn(name = "entity_id"))
    @Enumerated(EnumType.STRING)
    private Set<Genres> genres = new HashSet<>();

    public Set<Genres> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
    }

    public Artist() {
    }

    public Artist(String artistName, String artistInfo, String photo, String country, Set<Festival> festivals) {
        this.artistName = artistName;
        this.artistInfo = artistInfo;
        this.photo = photo;
        this.country = country;
       // this.festivals = festivals;
    }

//    public Set<Festival> getFestivals() {
//        return festivals;
//    }
//
//    public void setFestivals(Set<Festival> festivals) {
//        this.festivals = festivals;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistInfo() {
        return artistInfo;
    }

    public void setArtistInfo(String artistInfo) {
        this.artistInfo = artistInfo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
