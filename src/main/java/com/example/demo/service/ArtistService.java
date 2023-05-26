package com.example.demo.service;

import com.example.demo.model.Artist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ArtistService {
    public Artist getArtist(String name);

    public Flux<Artist> getArtistStream(String name);
}
