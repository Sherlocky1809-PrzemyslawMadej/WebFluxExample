package com.example.webfluxexample;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class MovieHandler {

    private final MovieService movieService;

    MovieHandler(MovieService movieService) {
        this.movieService = movieService;
    }

    Mono<ServerResponse> getAllMovies(ServerRequest request) {
        Optional<String> title = request.queryParam("title");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(movieService.getMovieByTitle(title), Movie.class);
    }

    // napisz funkcję, która zwróci obiekt typu movie po id (request param)
    Mono<ServerResponse> getOneMovieById(ServerRequest request) {

        String id = request.pathVariable("id");
        int integerId = Integer.parseInt(id);

        return movieService
                .getMovieById(integerId)
                .flatMap(movie ->  ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(movie))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(NoSuchElementException.class, e ->
                    ServerResponse.notFound().build());

//        return ServerResponse
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(movieService.getMovieById(integerId), Movie.class);
    }

    Mono<ServerResponse> addMovieToHandler(ServerRequest request) {
        Mono<Movie> movieMono = request.bodyToMono(Movie.class);
        return movieMono
                .flatMap(movie -> ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(movieService.addMovie(movie), Movie.class));
        // flatMap -> strumien mono na body zserializowane
    }

}
