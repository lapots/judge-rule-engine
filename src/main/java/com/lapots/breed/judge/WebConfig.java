package com.lapots.breed.judge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Configuration for router functions.
 */
@Configuration
public class WebConfig {

    /**
     * Router function.
     * @return string
     */
    @Bean
    public RouterFunction<?> helloRoute() {
        return route(GET("/judge/rest/hello"),
                request -> ServerResponse.ok().body(fromPublisher(Mono.just("Hello Router WebFlux"), String.class)));
    }

}
