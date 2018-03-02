package com.lapots.breed.judge;

import com.lapots.breed.judge.repository.InMemoryPlayerLevelCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Default initializations.
 */
@ComponentScan("com.lapots.breed.judge")
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

    /**
     * Inits cache.
     *
     * TODO:investigate better solution.
     *
     * @param cache cache bean
     * @return cmd runner
     */
    @Bean
    public CommandLineRunner initCache(final InMemoryPlayerLevelCache cache) {
        return args -> {
            cache.put(1, 100);
            cache.put(2, 1000);
            cache.put(3, 10000);
            cache.put(4, 100000);
        };
    }
}
