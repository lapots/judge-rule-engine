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

    def parseRules(String rulesFile) {
        def rules = new XmlSlurper().parseText(readResource("/level_up_rule.xml"))

        rules.rule.collect { rule ->
            Rule out = new Rule(name: rule.@name)
            // parse [inputs] section
            out.inputs = rule.inputs.input.collect {
                new Input(
                        fact: it.@fact,
                        name: it.@name,
                        type: it.@type,
                        access: it.@access
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
                        id: condition_element.@id,
                        type: condition_element.@type
                )
                condition.with {
                    left = new ConditionBlock(code: condition_element.left.code)
                    right = new ConditionBlock(code: condition_element.right.code)
                }
                condition
            }

            exec.bindings = rule.execution.bindings.binding.collect {
                def binding = new Binding(type: it.@type)
                binding.conditions = it.condition.collect { condition_element ->
                    exec.when.conditions.find { it.id == condition_element }
                }

                binding
            }
            out.execution = exec

            // parse [outputs] section
            out.outputs = rule.outputs.output.collect {
                new Output(
                        name: it.@name,
                        type: it.@type,
                        access: it.@access
                )
            }

            out
        }
    }

}
