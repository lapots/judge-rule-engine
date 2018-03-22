package com.lapots.breed.judge

import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder
import com.deliveredtechnologies.rulebook.model.RuleBook
import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter
import com.lapots.breed.judge.rulebook.core.ClassGenerator
import com.lapots.breed.judge.rulebook.core.RuleParser

// test
def ruleParser = new RuleParser()
def rules = ruleParser.parseRules("level_up_rule.xml")
def rule = rules[0]

def generator = new ClassGenerator()

Class<?> clazz = generator.generateClassForRule(rule, this.getClass().getClassLoader())
RuleBook ruleBook = RuleBookBuilder.create().addRule(new RuleAdapter(clazz.newInstance())).build()
println ruleBook.hasRules()


