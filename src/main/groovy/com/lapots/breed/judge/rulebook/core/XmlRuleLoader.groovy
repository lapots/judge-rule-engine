package com.lapots.breed.judge.rulebook.core

import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter
import com.lapots.breed.judge.rulebook.core.generator.api.IClassGenerator
import com.lapots.breed.judge.rulebook.domain.Rule

class XmlRuleLoader {

    static def loadRule(IClassGenerator generator, Rule rule) {
        new RuleAdapter(generator.generateRule(rule).newInstance())
    }
}
