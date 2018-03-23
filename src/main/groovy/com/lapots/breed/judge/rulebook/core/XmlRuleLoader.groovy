package com.lapots.breed.judge.rulebook.core

import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter
import com.lapots.breed.judge.rulebook.core.generator.api.IClassGenerator
import com.lapots.breed.judge.rulebook.domain.Rule

class XmlRuleLoader {

    static def loadRule(IClassGenerator generator, Rule rule) {
        new RuleAdapter(generator.generateRule(rule).newInstance())
    }

    static def loadRuleFromString(IClassGenerator generator, String xml) {
        def ruleParser = new RuleParser()
        // TODO: adjust flow
        def rules = ruleParser.parseRules(xml, false)

        def rule = rules[0]
        loadRule(generator, rule)
    }
}
