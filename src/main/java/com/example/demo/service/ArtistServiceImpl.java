package com.example.demo.service;

import com.example.demo.model.Artist;
import com.example.demo.model.Events;
import com.example.demo.model.Venues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService{

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient webClient;

    private static final String ARTIST_BASE_URL = "https://tm-event-mgmt.s3.eu-west-2.amazonaws.com/artists.json";
    private static final String EVENTS_BASE_URL = "https://tm-event-mgmt.s3.eu-west-2.amazonaws.com/events.json";
    private static final String VENUE_BASE_URL = "https://tm-event-mgmt.s3.eu-west-2.amazonaws.com/venues.json";

    @Override
    public Artist getArtist(String name) {

        ResponseEntity<Artist[]> responseEntity =
                restTemplate.getForEntity(ARTIST_BASE_URL, Artist[].class);

        Artist[] artistArray = responseEntity.getBody();

        ResponseEntity<Events[]> responseEntity2 =
                restTemplate.getForEntity(EVENTS_BASE_URL, Events[].class);
        Events[] eventArray = responseEntity2.getBody();

       /* ResponseEntity<Venues[]> responseEntity3 =
                restTemplate.getForEntity(ARTIST_BASE_URL, Venues[].class);
        Venues[] venueArray = responseEntity3.getBody();*/

        List<Artist> artists = Arrays.stream(artistArray)
                        .filter(a -> a.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        List<Events> events = Arrays.stream(eventArray)
                        .filter(e -> e.getArtists().stream().anyMatch(art-> art.getId().equals(artists.get(0).getId()))).collect(Collectors.toList());


        artists.get(0).setEvents(events);
        return artists.get(0);
    }


    @Override
    public Flux<Artist> getArtistStream(String name) {

        // reactive non blocking call

        Flux<Artist> fArtist =  webClient.get()
                .uri("/artists.json")
                .retrieve()
                .bodyToFlux(Artist.class)
                .filter(artist -> artist.getName().equalsIgnoreCase(name));

        Artist artist = fArtist.blockFirst();

        Flux<Events> fEvent = webClient.get()
                .uri("/events.json")
                .retrieve()
                .bodyToFlux(Events.class)
                .filter(e -> e.getArtists().stream().anyMatch(art -> art.getId().equals(artist.getId())));

        Mono<List<Events>> lEvent = fEvent.collectList();
        artist.setEvents(lEvent.block());
       /* fArtist.zipWith(fEvent)
                .subscribe(tuple -> {
                   System.out.println(tuple.getT1()+" ==== "+tuple.getT2());
                });*/
        return Flux.just(artist);
    }
}
