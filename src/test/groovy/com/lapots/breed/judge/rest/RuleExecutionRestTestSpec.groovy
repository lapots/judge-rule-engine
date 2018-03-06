package com.lapots.breed.judge.rest

import com.lapots.breed.judge.WebConfig
import com.lapots.breed.judge.domain.Player
import com.lapots.breed.judge.support.IntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher

/**
 * Integration test for rest.
 */
@ContextConfiguration(classes = [ WebConfig.class ])
@IntegrationTest
class RuleExecutionRestTestSpec extends Specification {

    @Autowired
    ApplicationContext context

    WebTestClient client

    def setup() {
        client = WebTestClient.bindToApplicationContext(context).build()
    }

    def "should increase player level"() {
        setup:
            def input = buildPlayer(1, 1, 1000)
            def output = buildPlayer(1, 2, 1000)
        expect:
            client.post().uri("/judge/rest/player")
                    .header("Content-Type", "application/json")
                    .body(fromPublisher(Mono.just(input), Player.class))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(Player.class)
                    .isEqualTo(output)
    }

    def "should not increase player level"() {
        setup:
        def input = buildPlayer(1, 1, 999)
        def output = buildPlayer(1, 1, 999)
        expect:
        client.post().uri("/judge/rest/player")
                .header("Content-Type", "application/json")
                .body(fromPublisher(Mono.just(input), Player.class))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Player.class)
                .isEqualTo(output)
    }

    def buildPlayer(int idx, int lvl, long exp) {
        def player = new Player()
        player.with {
            id = idx
            level = lvl
            experience = exp
        }

        player
    }
}
