package com.lapots.breed.judge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/judge/rest")
public class BasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicController.class);

    @GetMapping("/hello")
    public Mono<String> handle() {
        LOGGER.debug("Invoking hello controller");
        return Mono.just("Hello WebFlux");
    }

}
