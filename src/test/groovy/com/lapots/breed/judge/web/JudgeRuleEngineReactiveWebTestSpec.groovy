package com.lapots.breed.judge.web

import com.lapots.breed.judge.WebConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification;

/**
 * https://github.com/spring-projects/spring-boot/issues/10683
 */
@ContextConfiguration(classes = [ WebConfig.class ])
@WebFluxTest
class JudgeRuleEngineReactiveWebTestSpec extends Specification {

    @Autowired
    ApplicationContext context;

    WebTestClient webClient

    /*
        https://github.com/spockframework/spock/issues/76
     */
    def setup() {
        webClient = WebTestClient.bindToApplicationContext(context).build()
    }

    def "router function returns hello"() {
        expect:
            webClient.get().uri("/judge/rest/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Hello Router WebFlux")
    }

}
