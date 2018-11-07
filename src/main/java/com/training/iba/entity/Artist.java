package com.training.iba.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String artistName;
    private String artistInfo;
    private String photo;
    private String country;

    @ManyToMany
    @JoinTable(
            name = "fest_artists",
            joinColumns = {@JoinColumn(name = "artist_id")},
            inverseJoinColumns = {@JoinColumn(name = "fest_id")}
    )
    private Set<Festival> festivals = new HashSet<>();

    public Artist() {
    }

    public Artist(String artistName, String artistInfo, String photo, String country) {
        this.artistName = artistName;
        this.artistInfo = artistInfo;
        this.photo = photo;
        this.country = country;
    }

    public Set<Festival> getFestivals() {
        return festivals;
    }

    public void setFestivals(Set<Festival> festivals) {
        this.festivals = festivals;
    }

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

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", artistName='" + artistName + '\'' +
                ", artistInfo='" + artistInfo + '\'' +
                ", photo='" + photo + '\'' +
                ", country='" + country + '\'' +
                ", artists=" + festivals +
                '}';
    }
}
