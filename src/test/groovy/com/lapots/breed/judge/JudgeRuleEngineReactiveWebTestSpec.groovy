package com.lapots.breed.judge

import com.lapots.breed.judge.common.JudgeWebFluxAbstractSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * https://github.com/spring-projects/spring-boot/issues/10683
 */
class JudgeRuleEngineReactiveWebTestSpec extends JudgeWebFluxAbstractSpecification {

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
