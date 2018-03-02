package com.lapots.breed.judge.rest

import com.lapots.breed.judge.JudgeRuleEngineReactiveWebTestSpec
import com.lapots.breed.judge.domain.Player
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher

class RuleExecutionRestTestSpec extends JudgeRuleEngineReactiveWebTestSpec {

    @Autowired
    ApplicationContext context

    WebTestClient webClient

    def setup() {
        webClient = WebTestClient.bindToApplicationContext(context).build()
    }

    /*
        Default is:
            cache.put(1, 100);
            cache.put(2, 1000);
            cache.put(3, 10000);
            cache.put(4, 100000);
     */
    def "router should update player level"(int lvl, long exp, int nlvl) {
        setup:
            def original = JsonOutput.toJson(createPlayer(lvl, exp))
            def expected = createPlayer(nlvl, exp)
        expect:
            webClient.put().uri("/judge/rest/player")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(fromPublisher(Mono.just(original), String.class))
                    .exchange()
                        .expectStatus().isOk()
                        .expectBody(Player.class)
                    .isEqualTo(expected)
        where:
            lvl  | exp     | nlvl
              1  | 120     | 1
              1  | 1001    | 2
              3  | 12000   | 3
              4  | 150000  | 4

    }

    def createPlayer(int lvl, long exp) {
        def player = new Player()
        player.with {
            (level, experience) = [ lvl, exp ]
        }

        player
    }
}
