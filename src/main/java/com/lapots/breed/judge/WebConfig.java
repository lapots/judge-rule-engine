package com.lapots.breed.judge;

import com.lapots.breed.judge.domain.PlayerLevel;
import com.lapots.breed.judge.repository.PlayerLevelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@EnableJpaRepositories("com.lapots.breed.judge.repository")
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
     * Inits level repository.
     *
     * @param levelRepository cache bean
     * @return cmd runner
     */
    @Bean
    public CommandLineRunner initCache(final PlayerLevelRepository levelRepository) {
        return args -> {
            levelRepository.save(new PlayerLevel(1, 100));
            levelRepository.save(new PlayerLevel(2, 1000));
            levelRepository.save(new PlayerLevel(3, 10000));
            levelRepository.save(new PlayerLevel(4, 100000));
        };
    }
}
