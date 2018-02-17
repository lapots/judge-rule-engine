package com.lapots.breed.judge.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@WebFluxTest
class JudgeRuleEngineMvcTestSpec extends Specification {

    @Autowired
    WebTestClient webClient;

    def "mvc mono returns hello"() {
        expect:
            webClient.get().uri("/judge/rest/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                    .isEqualTo("Hello WebFlux")
    }

}
