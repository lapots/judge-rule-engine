package com.lapots.breed.judge;

import com.lapots.breed.judge.domain.PlayerLevel;
import com.lapots.breed.judge.repository.PlayerLevelRepository;
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
     * @param levelRepository cache bean
     * @return cmd runner
     */
    @Bean
    public CommandLineRunner initCache(final PlayerLevelRepository levelRepository) {
        return args -> {
            levelRepository.save(new PlayerLevel(1, 100));
            levelRepository.save(new PlayerLevel(2, 1000));
            levelRepository.save(new PlayerLevel(3, 10000));
            levelRepository.save(new PlayerLevel(4, 100000));
        };
    }
}
