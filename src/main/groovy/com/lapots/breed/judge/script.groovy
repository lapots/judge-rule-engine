package com.lapots.breed.judge

import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder
import com.deliveredtechnologies.rulebook.model.RuleBook
import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter

import com.lapots.breed.judge.rulebook.core.RuleParser
import com.lapots.breed.judge.rulebook.core.XmlRuleLoader
import com.lapots.breed.judge.rulebook.core.generator.BytecodeClassGenerator
import com.lapots.breed.judge.rulebook.core.generator.GroovyTemplateClassGenerator

// test
def ruleParser = new RuleParser()
def rules = ruleParser.parseRules("level_up_rule.xml")
def rule = rules[0]

templateCodeGeneratorSample(rule)
byteCodeGeneratorSample(rule)

def templateCodeGeneratorSample(rule) {
    RuleAdapter loadedRule = XmlRuleLoader.loadRule(new GroovyTemplateClassGenerator(), rule)
    RuleBook ruleBook = RuleBookBuilder.create().addRule(loadedRule).build()
    println ruleBook.hasRules()
}

def byteCodeGeneratorSample(rule) {
    RuleAdapter loadedRule = XmlRuleLoader.loadRule(new BytecodeClassGenerator(), rule)
    RuleBook ruleBook = RuleBookBuilder.create().addRule(loadedRule).build()
    println ruleBook.hasRules()
}
