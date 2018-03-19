package com.lapots.breed.judge

import com.lapots.breed.judge.rulebook.core.RuleParser

// test
def ruleParser = new RuleParser()
def rules = ruleParser.parseRules("level_up_rule.xml")
def rule = rules[0]

println "Rule name: $rule.name"
println "Rule facts:"
rule.inputs.each {
    println it
}
println "Rule outputs:"
rule.outputs.each {
    println it
}

println "Bindings"
rule.execution.bindings.eachWithIndex{ entry, i ->
    println "Index: $i"
    // TODO: investigate why it is a list
    println entry
}
