package com.lapots.breed.judge.rest

import com.deliveredtechnologies.rulebook.model.RuleBook
import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter
import com.lapots.breed.judge.WebConfig
import com.lapots.breed.judge.domain.Player

import com.lapots.breed.rule.builder.RuleClassGenerator
import com.lapots.breed.rule.compiler.OpenhftCachedCompiler
import com.lapots.breed.rule.generator.wrapper.ClassGeneratorWrapper
import com.lapots.breed.rule.parser.DefaultRuleParser
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Integration test for rest.
 */
@ContextConfiguration(classes = [ WebConfig.class ])
@RunWith(Sputnik.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerRuleExecutionRestTestSpec extends Specification {

    @LocalServerPort
    int port

    @Autowired
    ApplicationContext context

    TestRestTemplate restClient
    def serverUrl

    def setup() {
        RuleBook bean = context.getBean("ruleBook") as RuleBook
        bean.addRule(new RuleAdapter(buildClass()))

        restClient = new TestRestTemplate()
        serverUrl = "http://localhost:$port"
    }

    def "should increase player level"() {
        setup:
        def input = buildPlayer(1, 1, 1000)
        def output = buildPlayer(1, 2, 1000)
        expect:
        output == restClient.exchange("$serverUrl/judge/rest/player", HttpMethod.POST, entity(input), Player.class).getBody()
    }

    def "should not increase player level"() {
        setup:
        def input = buildPlayer(1, 1, 999)
        def output = buildPlayer(1, 1, 999)
        expect:
        output == restClient.exchange("$serverUrl/judge/rest/player", HttpMethod.POST, entity(input), Player.class).getBody()
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

    def buildClass() {
        def generator = new RuleClassGenerator()
                .withClassLoader(this.getClass().getClassLoader())
                .withCompiler(new OpenhftCachedCompiler())
                .withGenerator(new ClassGeneratorWrapper())
                .withBuilderType(RuleClassGenerator.BuilderType.FILE)
                .withParser(new DefaultRuleParser())
                .generate("level_up_rule.xml")
        generator[0].newInstance()
    }

    def entity(body) {
        HttpHeaders headers = new HttpHeaders()
        headers.set("Content-Type", "application/json")
        new HttpEntity(body, headers)
    }
}
