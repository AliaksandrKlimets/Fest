package com.training.iba.controller;

import com.training.iba.entity.Comment;
import com.training.iba.entity.Festival;
import com.training.iba.entity.Genres;
import com.training.iba.entity.User;
import com.training.iba.repository.FestivalRepository;
import com.training.iba.repository.UserRepository;
import com.training.iba.service.FestivalService;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/festivals")
public class FestivalController {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FestivalService festivalService;

    @GetMapping
    public List<Festival> festivals(@RequestParam("page") int page) {
        List<Festival> festivals = festivalService.filterFestivalsByEnded(festivalRepository.findAll());
        int pageCount = festivalService.getPageCount(festivals);
        if (page >= pageCount) return festivals.subList((pageCount - 1) * 3, festivals.size());
        else return festivals.subList((page - 1) * 3, page * 3);
    }

    @GetMapping("/page-count")
    public int festivalsPageCount() {
        List<Festival> festivals = festivalService.filterFestivalsByEnded(festivalRepository.findAll());
        return festivalService.getPageCount(festivals);
    }

    @GetMapping("/popular")
    public Iterable<Festival> getThreeMax() {
        return festivalService.getThreeMax();
    }

    @GetMapping("/genres/{genre}")
    public List<Festival> festivalsByGenre(@PathVariable String genre, @RequestParam("page") int page) {
        List<Festival> festivals = festivalService.filterFestivalsByEnded(festivalRepository.findByGenres(Genres.valueOf(genre)));
        int pageCount = festivalService.getPageCount(festivals);
        if (page >= pageCount) return festivals.subList((pageCount - 1) * 3, festivals.size());
        else return festivals.subList((page - 1) * 3, page * 3);
    }

    @GetMapping("/genres/{genre}/page-count")
    public int festivalsByGenresPageCount(@PathVariable String genre) {
        List<Festival> festivals = festivalService.filterFestivalsByEnded(festivalRepository.findByGenres(Genres.valueOf(genre)));
        return festivalService.getPageCount(festivals);
    }

    @GetMapping("/{id}")
    public Festival getFestival(@PathVariable long id) {
        Festival festival = festivalRepository.findById(id);
        Iterator<User> iterator = festival.getParticipants().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getActivationCode() != null) {
                iterator.remove();
            }
        }
        for (Comment comment : festival.getComments()) {
            for (User user : comment.getUsersLikes()) {
                comment.getLikes().add(user.getId());
            }
        }
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        if (festival.getFestDate().getTime() < date.getTime()) festival.setEnded(true);
        return festival;
    }

    @PostMapping("/add")
    public void addFestival(@RequestBody Festival festival) {
        festivalService.addFestival(festival);

    }

    @PostMapping("/{id}/add/anon")
    public User addAnon(@PathVariable("id") Festival festival, @RequestBody User user) {
        if (festivalService.addAnon(festival, user)) return user;
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFestival(@PathVariable("id") Festival festival) {
        festivalRepository.delete(festival);
    }

    @PutMapping("/update/festival/{id}")
    public void updateFestival(@RequestBody Festival festival, @PathVariable("id") long id) {
        festivalService.updateFestival(festival, id);
    }

    @GetMapping("/close/available/{id}")
    public void closeAvailable(@PathVariable("id") long id) {
        Festival festival = festivalRepository.findById(id);
        festival.setAvailable(false);
        festivalRepository.save(festival);
    }

    @GetMapping("/open/available/{id}")
    public void openAvailable(@PathVariable("id") long id) {
        Festival festival = festivalRepository.findById(id);
        festival.setAvailable(true);
        festivalRepository.save(festival);
    }

    @PostMapping("/attend/{id}")
    public void attend(@PathVariable("id") long id, @RequestBody long festId) {
        User user = userRepository.findById(id);
        Festival festival = festivalRepository.findById(festId);
        user.getFestivals().add(festival);
        userRepository.save(user);
    }

    @PostMapping("/not-attend/{id}")
    public void notAttend(@PathVariable("id") long id, @RequestBody long festId) {
        User user = userRepository.findById(id);
        Festival festival = festivalRepository.findById(festId);
        user.getFestivals().remove(festival);
        userRepository.save(user);
    }

    @GetMapping("users/{id}/festivals")
    public List<Festival> userFestivals(@PathVariable("id") User user) {
        List<Festival> festivals = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()) {
            if (festival.getParticipants().contains(user)) festivals.add(festival);
        }
        return festivals.stream().sorted((Festival a, Festival b) ->
                Long.valueOf(a.getFestDate().getTime()).compareTo(Long.valueOf(b.getFestDate().getTime()))).collect(Collectors.toList());
    }

    @GetMapping("users/{id}/festivals/future")
    public List<Festival> userFestivalsFuture(@PathVariable("id") User user) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        List<Festival> festivals = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()) {
            if (festival.getParticipants().contains(user)) festivals.add(festival);
        }
        return festivals.stream().filter(item -> item.getFestDate().getTime() > date.getTime()).sorted((Festival a, Festival b) ->
                Long.valueOf(a.getFestDate().getTime()).compareTo(Long.valueOf(b.getFestDate().getTime()))).collect(Collectors.toList());
    }

    @GetMapping("/search/{email}")
    public Iterable<Festival> getFestsByEmail(@PathVariable("email") String email) {
        return festivalService.findByEmail(email);
    }

    @GetMapping("/filter")
    public List<Festival> doFilterAll(@RequestParam boolean isAvailable,
                                      @RequestParam boolean isFree, @RequestParam boolean isEarly,
                                      @RequestParam int page) {
        List<Festival> festivals = festivalService.filterFestivalsByEnded(festivalRepository.findAll());
        festivals = festivalService.filter(festivals, isAvailable, isFree, isEarly);
        int pageCount = festivalService.getPageCount(festivals);
        if (page >= pageCount) return festivals.subList((pageCount - 1) * 3, festivals.size());
        else return festivals.subList((page - 1) * 3, page * 3);
    }

    @GetMapping("/filter/page-count")
    public int getPageCountAfterFilter(@RequestParam boolean isAvailable,
                                       @RequestParam boolean isFree, @RequestParam boolean isEarly) {
        List<Festival> festivals = festivalService.filterFestivalsByEnded(festivalRepository.findAll());
        return festivalService.getPageCount(festivalService.filter(festivals, isAvailable, isFree, isEarly));
    }

    @GetMapping("/genres/{genre}/filter")
    public List<Festival> doFilterGenres(@PathVariable String genre, @RequestParam boolean isAvailable,
                                         @RequestParam boolean isFree, @RequestParam boolean isEarly,
                                         @RequestParam int page) {
        List<Festival> festivals = festivalService.filterFestivalsByEnded(festivalRepository.findByGenres(Genres.valueOf(genre)));
        festivals = festivalService.filter(festivals, isAvailable, isFree, isEarly);
        int pageCount = festivalService.getPageCount(festivals);
        if (page >= pageCount) return festivals.subList((pageCount - 1) * 3, festivals.size());
        else return festivals.subList((page - 1) * 3, page * 3);
    }

    @GetMapping("/genres/{genre}/filter/page-count")
    public int getPageCountAfterFilterByGenre(@PathVariable String genre, @RequestParam boolean isAvailable,
                                              @RequestParam boolean isFree, @RequestParam boolean isEarly) {
        List<Festival> festivals = festivalService.filterFestivalsByEnded(festivalRepository.findByGenres(Genres.valueOf(genre)));
        return festivalService.getPageCount(festivalService.filter(festivals, isAvailable, isFree, isEarly));
    }

    @GetMapping("/search")
    public List<Festival> searchFestivalsByName(@RequestParam String name) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        List<Festival> festivals = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()) {
            if (festival.getFestDate().getTime() < date.getTime()) festival.setEnded(true);
            festivals.add(festival);
        }
        return festivals.stream().filter(data -> data.getFestivalName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
}