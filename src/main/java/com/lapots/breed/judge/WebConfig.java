package com.lapots.breed.judge;

import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.deliveredtechnologies.rulebook.model.runner.RuleAdapter;
import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.domain.PlayerLevel;
import com.lapots.breed.judge.repository.PlayerLevelRepository;
import com.lapots.breed.judge.repository.PlayerRepository;
import com.lapots.breed.rule.builder.RuleClassGenerator;
import com.lapots.breed.rule.compiler.OpenhftCachedCompiler;
import com.lapots.breed.rule.generator.wrapper.ClassGeneratorWrapper;
import com.lapots.breed.rule.parser.DefaultRuleParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.InvalidClassException;
import java.util.List;

/**
 * Default initializations.
 */
@ComponentScan("com.lapots.breed.judge")
@EnableJpaRepositories("com.lapots.breed.judge.repository")
@Configuration
public class WebConfig {
    /**
     * Inits level repository.
     *
     * @param levelRepository level repository
     * @param playerRepository player repository
     * @return cmd runner
     */
    @Bean
    public CommandLineRunner initCache(final PlayerLevelRepository levelRepository,
                                       final PlayerRepository playerRepository) {
        return args -> {
            levelRepository.save(new PlayerLevel(1, 100));
            levelRepository.save(new PlayerLevel(2, 1000));
            levelRepository.save(new PlayerLevel(3, 10000));
            levelRepository.save(new PlayerLevel(4, 100000));

            // just for consistency
            playerRepository.save(new Player(1, 3, 15000L));
            playerRepository.save(new Player(2, 4, 100000));
        };
    }

    /**
     * Returns rule book bean.
     * @return rule book
     */
    @Bean
    public RuleBook ruleBook() {
        return RuleBookBuilder.create().build();
    }

    /**
     * Instantiates rule engine with simple rule.
     * @param ruleBook rule book
     * @return cmd runner
     */
    @Bean
    public CommandLineRunner initRuleEngine(final RuleBook ruleBook) {
        return args -> {
            List<Class<?>> rules = new RuleClassGenerator()
                    .withBuilderType(RuleClassGenerator.BuilderType.FILE)
                    .withClassLoader(this.getClass().getClassLoader())
                    .withCompiler(new OpenhftCachedCompiler())
                    .withGenerator(new ClassGeneratorWrapper())
                    .withParser(new DefaultRuleParser())
                    .generate("level_up_rule.xml");
            rules.stream().map(ruleClass -> {
                try {
                    return ruleClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new IllegalStateException("Failed to process parsed rule!", e);
                }
            }).map(rule -> {
                try {
                    return new RuleAdapter(rule);
                } catch (InvalidClassException e) {
                    throw new IllegalStateException("Failed to create rule adapter!", e);
                }
            }).forEach(ruleBook::addRule);
        };
    }
}
