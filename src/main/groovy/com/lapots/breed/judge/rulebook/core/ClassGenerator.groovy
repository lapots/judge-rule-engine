package com.lapots.breed.judge.rulebook.core

import com.deliveredtechnologies.rulebook.annotation.Given
import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter
import com.google.common.base.CaseFormat
import com.lapots.breed.judge.rulebook.domain.Rule
import com.lapots.breed.judge.rulebook.domain.data.Input
import net.bytebuddy.ByteBuddy
import net.bytebuddy.description.annotation.AnnotationDescription
import net.bytebuddy.description.type.TypeDefinition
import net.bytebuddy.dynamic.DynamicType
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy
import net.bytebuddy.jar.asm.Opcodes

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

    def genClassFields(Rule rule, DynamicType.Builder<?> dynamicType) {
        // generates outputs and inputs
        rule.inputs.each { Input input ->
            def annotationDesc = AnnotationDescription.Builder
                    .ofType(Given.class)
                    .define("value", input.fact)
                    .build()

            def type = types[input.type] ? types[input.type] : input.type

            dynamicType= dynamicType
                    .defineField(input.name, TypeDefinition.forName(type), accesses[input.access])
                    .annotateField(annotationDesc)
        }

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
