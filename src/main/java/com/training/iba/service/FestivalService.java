package com.training.iba.service;

import com.training.iba.entity.Artist;
import com.training.iba.entity.Comment;
import com.training.iba.entity.Festival;
import com.training.iba.entity.User;
import com.training.iba.repository.ArtistRepository;
import com.training.iba.repository.CommentRepository;
import com.training.iba.repository.FestivalRepository;
import com.training.iba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MailSender mailSender;

    public List<Festival> getAvailable() {
        List<Festival> festivals = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()) {
            if (festival.isAvailable()) festivals.add(festival);
        }
        return festivals;
    }

    public List<Festival> getThreeMax() {
        List<Festival> festivals = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()) {
            festivals.add(festival);
        }
        filterFestivalsByActive(festivals);
        Comparator<Festival> comparator = new Comparator<Festival>() {
            @Override
            public int compare(Festival o1, Festival o2) {
                return o2.getParticipants().size() - o1.getParticipants().size();
            }
        };
        return festivals.stream().sorted(comparator).limit(3).collect(Collectors.toList());
    }

    public void addFestival(Festival festival) {
        festival.setAvailable(true);
        Set<Artist> set = new HashSet<>();

        for (long l : festival.getArtistsIdList()) {
            Artist artist = artistRepository.findById(l);
            set.add(artist);
        }
        festival.setArtists(set);
        festivalRepository.save(festival);
        String message = "We invite you to visit our new festival \"" + festival.getFestivalName()
                + "\" on " + festival.getFestDate() + ". We hope to see you!";
        mailing(message, "New festival!");
    }

    private void mailing(String message, String theme) {
        for (User user : userRepository.findAll()) {
            if (!StringUtils.isEmpty(user.getEmail()) && !user.isAnon() && user.isMailingAccess()) {
                mailSender.send(user.getEmail(), theme, "Hi, " + user.getName() + "! " + message);
            }
        }
    }

    public void updateFestival(Festival festival, long id) {
        Festival fest = festivalRepository.findById(id);
        festival.setId(id);
        Set<Artist> set = new HashSet<>();

        for (long l : festival.getArtistsIdList()) {
            Artist artist = artistRepository.findById(l);
            set.add(artist);
        }
        festival.setArtists(set);
        festival.setParticipants(fest.getParticipants());
        festivalRepository.save(festival);
    }

    public List<Festival> findByEmail(String email) {
        List<Festival> festivals = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()) {
            for (User user : festival.getParticipants()) {
                if (user.getEmail().equals(email) && user.isAnon() && user.getActivationCode() == null) {
                    festivals.add(festival);
                    break;
                }
            }
        }
        return festivals;
    }

    public boolean addAnon(Festival festival, User user) {
        for (User user1 : festival.getParticipants()) {
            if (user1.getEmail().equals(user.getEmail())) return false;
        }

        user.setAnon(true);
        user.getFestivals().add(festival);
        user.setActivationCode(UUID.randomUUID().toString());
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = "Hello, " + user.getName() + "! " +
                    "Visit link to apply your attention: " + "http://localhost:4200/activate/" + user.getActivationCode();
            mailSender.send(user.getEmail(), "Festival attention request", message);
        }
        userRepository.save(user);
        return true;
    }

    public boolean confirmAttention(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    public Iterable<Festival> filterFestivalsByActive(Iterable<Festival> festivals) {
        for (Festival festival : festivals) {
            Iterator<User> iterator = festival.getParticipants().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getActivationCode() != null) {
                    iterator.remove();
                }
            }
        }
        return festivals;
    }

    public Comment addComment(long festId, long userId, String text) {
        Comment comment = new Comment();
        Festival festival = festivalRepository.findById(festId);
        User author = userRepository.findById(userId);
        comment.setText(text);
        comment.setAuthor(author.getUsername());
        comment.setAuthorId(author.getId());
        comment.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        festival.getComments().add(comment);
        commentRepository.save(comment);
        festivalRepository.save(festival);
        return comment;
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Festival> filter(List<Festival> festivals, boolean isAvailable, boolean isFree, boolean isEarly) {
        if (isAvailable) {
            festivals = festivals.stream().filter(item -> item.isAvailable()).collect(Collectors.toList());
        }
        if (isFree) {
            festivals = festivals.stream().filter(item -> item.getCost() == 0).collect(Collectors.toList());
        }

        if (isEarly) {
            festivals = festivals.stream().sorted((Festival a, Festival b) ->
                    Long.valueOf(a.getFestDate().getTime()).compareTo(Long.valueOf(b.getFestDate().getTime()))).collect(Collectors.toList());
        }
        return festivals;
    }

    public int getPageCount(List<Festival> festivals) {
        if (festivals.size() % 3 == 0 && festivals.size()>0) return festivals.size() / 3;
        else return festivals.size() / 3 + 1;
    }

    public List<Festival> filterFestivalsByEnded(Iterable<Festival> fests) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        List<Festival> festivals = new ArrayList<>();
        for (Festival festival : filterFestivalsByActive(fests)) {
            if (festival.getFestDate().getTime() > date.getTime())
                festivals.add(festival);
        }
        return festivals;
    }
}
