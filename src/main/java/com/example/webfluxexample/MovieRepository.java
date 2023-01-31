package com.example.webfluxexample;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
class MovieRepository {

//    Podejście imperatywne:
//    List<Movie> findAll() {
//        return null;
//    }

    private final List<Movie> movies = new ArrayList<>();

    public MovieRepository() {
        movies.add(new Movie(1,"Star Wars", "Star Wars Force Awakane"));
        movies.add(new Movie(2, "Batman", "Batman Rises"));
    }

    private final Flux<Movie> movieFlux = Flux.fromIterable(movies);

    Flux<Movie> findAll() {
        return movieFlux;
    }

    Mono<Movie> findMovieById(int id) {
        return movieFlux
                .filter(movie -> movie.getId() == id)
                .single();
    }

    Mono<Movie> addMovie(Movie movie) {
        movie.setId(new Random().nextInt(100000));
        movies.add(movie);
        return Mono.just(movie);
    }

}
