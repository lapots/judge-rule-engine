package com.lapots.breed.judge.service.rule

import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder
import com.deliveredtechnologies.rulebook.model.RuleBook
import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter
import com.lapots.breed.judge.domain.Player
import com.lapots.breed.judge.repository.wrapper.api.IPlayerLevelRepository
import com.lapots.breed.rule.builder.RuleClassGenerator
import com.lapots.breed.rule.compiler.OpenhftCachedCompiler
import com.lapots.breed.rule.generator.wrapper.ClassGeneratorWrapper
import com.lapots.breed.rule.parser.DefaultRuleParser
import spock.lang.Specification

class RuleExecutionServiceTestSpec extends Specification {

    def "should level up player"(int pL, long exp, int nL, int cL, int mL) {
        setup:
            IPlayerLevelRepository levelRep = Mock {
                findClosestLevel(_) >> cL
                findMaxLevel() >> mL
            }
            RuleBook runner = RuleBookBuilder
                    .create()
                    .addRule(new RuleAdapter(buildClass()))
                    .build()
            def ruleExecutionService = new RuleExecutionService(runner, levelRep)

            def input = buildPlayer(pL, exp)
        expect:
            buildPlayer(nL, exp) == ruleExecutionService.levelUpPlayer(input)
        where:
             pL   |   exp     |   nL  |   cL   |   mL
                1 | 1000      | 2     | 2      | 5
                2 | 9999      | 2     | 2      | 5
                5 | 1000000   | 5     | 5      | 5

    }

    def buildPlayer(int lvl, long exp) {
        def player = new Player()
        player.with {
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
}
