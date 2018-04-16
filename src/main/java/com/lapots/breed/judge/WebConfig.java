package com.lapots.breed.judge;

import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.domain.PlayerLevel;
import com.lapots.breed.judge.repository.PlayerLevelRepository;
import com.lapots.breed.judge.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
}
