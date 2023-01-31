package com.example.webfluxexample;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
class MovieService {

    private final MovieRepository movieRepository;

    MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    Flux<Movie> getAll() {
        return movieRepository.findAll();
    }

    Mono<Movie> getMovieById(int id) {
        return movieRepository.findMovieById(id);
    }

    Mono<Movie> addMovie(Movie movie) {
        return movieRepository.addMovie(movie);
    }

    Flux<Movie> getMovieByTitle(Optional<String> title) {
        return title.map(queryParam ->
                movieRepository.findAll()
                        .filter(movie -> movie.getTitle().contains(queryParam)))
                .orElseGet(movieRepository::findAll);
    }
}
