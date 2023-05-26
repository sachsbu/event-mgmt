package com.example.demo;

import ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory;
import com.example.demo.model.Artist;
import com.example.demo.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	ArtistService artistService;

	private BeanDescriptionFactory StepVerifier;

	@Test
	void contextLoads() {
	}

	@Test
	void testGetArtistStream(){
		Flux<Artist> value = artistService.getArtistStream("Colosseum");
		assertEquals(value.blockFirst().getId(), "22");
	}

}
