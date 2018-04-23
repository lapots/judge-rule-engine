package com.lapots.breed.judge.utils;

import com.lapots.breed.rule.builder.RuleClassGenerator;
import com.lapots.breed.rule.compiler.OpenhftCachedCompiler;
import com.lapots.breed.rule.generator.wrapper.ClassGeneratorWrapper;
import com.lapots.breed.rule.parser.DefaultRuleParser;
import lombok.experimental.UtilityClass;

/**
 * Utility class for rule generation.
 */
@UtilityClass
public class RuleBuildingUtils {

    /**
     * Default generator for FILE parsing.
     * @return generator
     */
    public RuleClassGenerator defaultFileGenerator() {
        return new RuleClassGenerator()
                .withBuilderType(RuleClassGenerator.BuilderType.FILE)
                .withClassLoader(RuleBuildingUtils.class.getClassLoader())
                .withCompiler(new OpenhftCachedCompiler())
                .withGenerator(new ClassGeneratorWrapper())
                .withParser(new DefaultRuleParser());
    }

}
