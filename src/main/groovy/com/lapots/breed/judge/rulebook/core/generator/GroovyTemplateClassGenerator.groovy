package com.lapots.breed.judge.rulebook.core.generator

import com.google.common.base.CaseFormat
import com.lapots.breed.judge.rulebook.core.generator.api.IClassGenerator
import com.lapots.breed.judge.rulebook.domain.Rule
import com.lapots.breed.judge.rulebook.domain.data.Input
import groovy.text.SimpleTemplateEngine

class GroovyTemplateClassGenerator implements IClassGenerator {

    private static final PACKAGE = "com.lapots.breed.judge.domain.rule"

    def bindingsMap = [
            "and" : "&&"
    ]

    def typesMap = [
            "player" : "com.lapots.breed.judge.domain.Player"
    ]

    def conditionsMap = [
            "not_equals" : "!=",
            "less_than" : "<"
    ]

    @Override
    Class<?> generateRule(Rule rule) {
        def templateFile = this.getClass().getResource("/rule_template.template")
        def code = genClass(templateFile, rule)
        compileClass(code)
    }

    def private genClass(URL templateFile, Rule data) {
        def binding = createMap(data)
        def templateEngine = new SimpleTemplateEngine()
        templateEngine.createTemplate(templateFile).make(binding).toString()
    }

    def private compileClass(String code) {
        new GroovyClassLoader().parseClass(code)
    }

    def private createMap(Rule rule) {
        def map = [:]
        map = populateCommon(map, rule)
        map = populateImports(map, rule)
        map = populateFields(map, rule)
        map = populateLhs(map, rule)
        map = populateRhs(map, rule)
        map
    }

    def private populateCommon(map, Rule rule) {
        map["pkg"] = PACKAGE
        map["ruleName"] = rule.name

        String className = rule.name.replaceAll(" ", "_").toUpperCase()
        className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, className)
        map["className"] = className

        map
    }

    def private populateImports(map, Rule rule) {
        // imports
        def imports = rule.inputs.collect { it.type }
        imports += rule.outputs.collect { it.type }
        imports = imports.findAll { !(it ==~ /java.lang.*/) && !(it ==~ /int/) } // find better way
        imports = imports.collect { typesMap[it] }
        imports = imports.unique()
        map["objectImports"] = imports

        map
    }

    def private populateFields(map, Rule rule) {
        // fields
        def fields = rule.inputs.collect() + rule.outputs.collect()
        fields = fields
                .groupBy { it.name }
                .entrySet()
                .sort { a, b -> a.value[0].type <=> b.value[0].type }
        fields = fields.collect { entry ->
            // @Given and @Result fields only - so only two items at max for now
            def annotations = entry.value.collect { io ->
                if (io instanceof Input) {
                    "@Given(\"$io.fact\")"
                } else {
                    "@Result"
                }
            }
            // all needed fields are the same for both objects
            def anyType = entry.value[0]
            def importType = typesMap[anyType.type] ? typesMap[anyType.type] : anyType.type
            def output = annotations.join(' ')
            output += " $anyType.access ${ importType.substring(importType.lastIndexOf('.') + 1) } $anyType.name;"
        }
        map["fields"] = fields

        map
    }

    def private populateLhs(map, Rule rule) {
        def bindings = rule.execution.bindings
        def when = rule.execution.when

        def conditions = when.conditions.groupBy { it.id }
        def processedConditions = conditions.collectEntries { k, v ->
            def code = ""

            // due to grouping it creates pairs with k : [v]
            def leftBlock = v[0].left
            if (leftBlock.code) { code += leftBlock.code }

            def operator = conditionsMap[v[0].type]
            code += " " + operator + " "

            def rightBlock = v[0].right
            if (rightBlock.code) { code += rightBlock.code }

            [k, code]
        }

        // single record I presume
        def result = bindings.collect { b ->
            def type = " ${ bindingsMap[b.type] } "
            b.conditions.collect { processedConditions[it] }.join(type)
        }

        map["lhs"] = result[0] + ";"
        map
    }

    def private populateRhs(map, Rule rule) {
        def then = rule.execution.then

        if (then.code) { map["rhs"] = then.code }
        else { map["rhs"] = "" }

        map
    }
}
