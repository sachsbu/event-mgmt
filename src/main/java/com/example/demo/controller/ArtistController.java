package com.example.demo.controller;

import com.example.demo.model.Artist;
import com.example.demo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @GetMapping("/artist/{name}")
    public ResponseEntity getArtistByName(@PathVariable("name") String name){
        Artist artist = artistService.getArtist(name);
        return new ResponseEntity(artist, HttpStatus.OK);
    }

    @GetMapping(value = "/artist/stream/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Artist> getArtistByNameStream(@PathVariable("name") String name){
        Flux<Artist> artist = artistService.getArtistStream(name);
        return artist;
    }
}
