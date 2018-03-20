package com.lapots.breed.judge.rulebook.core

import com.deliveredtechnologies.rulebook.annotation.Given
import com.deliveredtechnologies.rulebook.annotation.Result
import com.deliveredtechnologies.rulebook.annotation.When
import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter
import com.google.common.base.CaseFormat
import com.lapots.breed.judge.rulebook.domain.Rule
import com.lapots.breed.judge.rulebook.domain.data.Input
import com.lapots.breed.judge.rulebook.domain.data.Output
import net.bytebuddy.ByteBuddy
import net.bytebuddy.description.annotation.AnnotationDescription
import net.bytebuddy.description.type.TypeDefinition
import net.bytebuddy.dynamic.DynamicType
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy
import net.bytebuddy.implementation.FixedValue
import net.bytebuddy.jar.asm.Opcodes

import static net.bytebuddy.matcher.ElementMatchers.named
import static net.bytebuddy.matcher.ElementMatchers.returns
import static net.bytebuddy.matcher.ElementMatchers.takesArguments

class ClassGenerator {

    def package_path = "com.lapots.breed.judge.domain.rule"
    def accesses = [
            "private"   : Opcodes.ACC_PRIVATE,
            "public"    : Opcodes.ACC_PUBLIC
    ]
    def types = [
            "int"       : "java.lang.Integer"
    ]

    /** the goal is to generate
        {@link com.lapots.breed.judge.domain.rule.LevelUpRule}
    */
    def generateClassForRule(Rule rule, ClassLoader classLoader) {
        // generate class skeleton
        DynamicType.Builder<?> dynamicType = genClassSkeleton((String) rule.name)
        dynamicType = genClassFields(rule, dynamicType)
        dynamicType = genMethods(rule, dynamicType)

        // generate Rule adapter
        def generatedCode = dynamicType.make()
        generatedCode.saveIn(new File("build/generated_classes"))

        genRule(generatedCode, classLoader)
    }

    def genClassSkeleton(String rule_name) {
        // generate class name
        String className = rule_name.replaceAll(" ", "_").toUpperCase()
        className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, className)

        // generate annotation above class
        def annotationDesc = AnnotationDescription.Builder
                .ofType(com.deliveredtechnologies.rulebook.annotation.Rule.class)
                .define("name", (String) rule_name)
                .build()

        // generate class skeleton
        new ByteBuddy()
                .subclass(Object.class)
                .name(package_path + "." + className)
                .annotateType(annotationDesc)
    }

    // TODO: refactoring
    def genClassFields(Rule rule, DynamicType.Builder<?> dynamicType) {
        // need some preprocessing for grouping
        def groupedInputs = rule.inputs.groupBy { it.name }
        def groupedOutputs = rule.outputs.groupBy { it.name }

        def newMap = groupedInputs.collectEntries { entry ->
            if (groupedOutputs[entry.key]) { // output contain the same key
                def newValue = [ *entry.value, *groupedOutputs[entry.key] ]
                [ entry.key, newValue ]
            } else {
                [ entry.key, *entry.value ]
            }
        }

        groupedOutputs.each { k, v ->
            if (!newMap[k]) {
                newMap[k] = v
            }
        }

        newMap.each { fieldName, types ->
            def inputAnnotationDesc, outputAnnotationDesc, fieldType, access
            types.each { type ->
                if (type instanceof Input) {
                    fieldType = type.type
                    access = type.access
                    inputAnnotationDesc = AnnotationDescription.Builder
                            .ofType(Given.class)
                            .define("value", type.fact)
                            .build()
                } else if (type instanceof Output) {
                    fieldType = type.type
                    access = type.access
                    outputAnnotationDesc = AnnotationDescription.Builder
                            .ofType(Result.class)
                            .build()
                }
            }

            def type = this.types[fieldType] ? this.types[fieldType] : fieldType
            dynamicType = dynamicType.defineField(fieldName, TypeDefinition.forName(type), accesses[access])

            if (inputAnnotationDesc && !outputAnnotationDesc) {
                dynamicType = dynamicType.annotateField(inputAnnotationDesc)
            } else if (!inputAnnotationDesc && outputAnnotationDesc) {
                dynamicType = dynamicType.annotateField(outputAnnotationDesc)
            } else {
                dynamicType = dynamicType.annotateField(inputAnnotationDesc, outputAnnotationDesc)
            }
        }
        dynamicType
    }

    def genMethods(Rule rule, DynamicType.Builder<?> dynamicType) {
        def execution = rule.execution

        def whenDesc = AnnotationDescription.Builder
                .ofType(When.class)
                .build()
        // when
        dynamicType = dynamicType
                .method(named("when").and(returns(Boolean.class)).and(takesArguments(0)))
                .intercept(FixedValue.value(true))
                .annotateMethod(whenDesc)
        /*
        dynamicType = dynamicType.method(named("then"))
        execution.bindings // &&, || and so on
        execution.when
        execution.then
        */
        dynamicType
    }

    def genRule(DynamicType.Unloaded<?> dynamicType, ClassLoader classLoader) {
        // create new class loader based on the provided as a parent to store external rules
        // in a separate classloader in a sense
        Class<?> clazz =
                dynamicType.load(classLoader, ClassLoadingStrategy.Default.WRAPPER).getLoaded() // returns Class<?>
        new RuleAdapter(clazz.newInstance()) // add it as rule to rule book
    }
}
