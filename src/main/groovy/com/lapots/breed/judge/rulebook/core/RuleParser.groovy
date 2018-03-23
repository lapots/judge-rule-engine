package com.lapots.breed.judge.rulebook.core

import com.lapots.breed.judge.rulebook.domain.Rule
import com.lapots.breed.judge.rulebook.domain.data.Input
import com.lapots.breed.judge.rulebook.domain.data.Output
import com.lapots.breed.judge.rulebook.domain.execution.Execution
import com.lapots.breed.judge.rulebook.domain.execution.ThenBlock
import com.lapots.breed.judge.rulebook.domain.execution.WhenBlock
import com.lapots.breed.judge.rulebook.domain.execution.binding.Binding
import com.lapots.breed.judge.rulebook.domain.execution.condition.Condition
import com.lapots.breed.judge.rulebook.domain.execution.condition.ConditionBlock

import static com.lapots.breed.judge.rulebook.core.util.XmlProcessingUtils.readResource

class RuleParser {

    def parseRules(String xml, boolean isFile) {
        if (isFile) {
            def rules = new XmlSlurper().parseText(readResource(xml))
            return parseRulesXml(rules)
        } else {
            def rules = new XmlSlurper().parseText(xml)
            return parseRulesXml(rules)
        }
    }

    def private parseRulesXml(rules) {
        rules.rule.collect { rule ->
            Rule out = new Rule(name: rule.@name.text())
            // parse [inputs] section
            out.inputs = rule.inputs.input.collect {
                new Input(
                        fact: it.@fact.text(),
                        name: it.@name.text(),
                        type: it.@type.text(),
                        access: it.@access.text()
                )
            }

            // parse [execution] section
            Execution exec = new Execution()
            exec.then = new ThenBlock(
                    code: rule.execution.then.code.text()
            )
            exec.when = new WhenBlock()
            exec.when.conditions = rule.execution.when.conditions.condition.collect { condition_element ->
                def condition = new Condition(
                        id: condition_element.@id.text(),
                        type: condition_element.@type.text()
                )
                condition.with {
                    left = new ConditionBlock(code: condition_element.left.code.text())
                    right = new ConditionBlock(code: condition_element.right.code.text())
                }
                condition
            }

            exec.bindings = rule.execution.bindings.binding.collect {
                def binding = new Binding(type: it.@type.text())
                binding.conditions = it.condition.collect { it.text() }
                binding
            }
            out.execution = exec

            // parse [outputs] section
            out.outputs = rule.outputs.output.collect {
                new Output(
                        name: it.@name.text(),
                        type: it.@type.text(),
                        access: it.@access.text()
                )
            }

            out
        }
    }
}
