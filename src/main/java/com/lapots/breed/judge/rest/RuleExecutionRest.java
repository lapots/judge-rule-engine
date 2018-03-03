package com.lapots.breed.judge.rest;

import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.service.PlayerRuleCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Rest for rule execution.
 */
@Configuration
public class RuleExecutionRest {

    @Autowired
    private PlayerRuleCalculationService service;

    /**
     * Increases player level if needed.
     * @return player
     */
    @Bean
    public RouterFunction<?> validatePlayer() {
        return route(PUT("/judge/rest/player"),
            request -> {
                Mono<Player> player = request.bodyToMono(Player.class);
                return ServerResponse.ok().body(fromPublisher(service.levelUp(player), Player.class));
            });
    }

}