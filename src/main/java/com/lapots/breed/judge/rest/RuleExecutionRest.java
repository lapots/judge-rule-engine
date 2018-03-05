package com.lapots.breed.judge.rest;

import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.service.PlayerRuleCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Rest for rule execution.
 */
@Configuration
public class RuleExecutionRest {
    private PlayerRuleCalculationService service;

    /**
     * Constructor injection.
     * @param service calculation service
     */
    @Autowired
    public RuleExecutionRest(final PlayerRuleCalculationService service) {
        this.service = service;
    }

    /**
     * Increases player level if needed.
     * @return player
     */
    @Bean
    public RouterFunction<?> validatePlayer() {
        return route(POST("/judge/rest/player"),
            request -> ServerResponse.ok().body(
                    fromPublisher(request.bodyToMono(Player.class).map(p -> service.levelUp(p)), Player.class)
            )
        );
    }

}
