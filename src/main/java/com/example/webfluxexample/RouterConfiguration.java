package com.example.webfluxexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.awt.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

//    send request 1 => subscribe to handler, wait async on response
//    send request 2 => subscribe to handler, wait async on response
//    send request 3 => subscribe to handler, wait async on response
//    send request 4 => subscribe to handler, wait async on response

    @Bean
    RouterFunction<ServerResponse> routes(MovieHandler movieHandler) {
        return route(GET("/movies"), movieHandler::getAllMovies)
                .andRoute(GET("/movies/{id}"), movieHandler::getOneMovieById)
                .andRoute(POST("/movies").and(accept(MediaType.APPLICATION_JSON)),
                        movieHandler::addMovieToHandler);
    }

}
