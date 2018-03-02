package com.lapots.breed.judge;

import com.lapots.breed.judge.repository.InMemoryPlayerLevelCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application.
 */
@ComponentScan({"com.lapots.breed.judge"})
@SpringBootApplication
public class JudgeRuleEngineApplication {

    /**
     * Main application.
     * @param args console args
     */
    public static void main(final String[] args) {
        SpringApplication.run(JudgeRuleEngineApplication.class, args);
    }

    /**
     * Inits cache.
     *
     * TODO:investigate better solution.
     *
     * @param cache cache bean
     * @return cmd runner
     */
    @Bean
    public CommandLineRunner initCache(final InMemoryPlayerLevelCache cache) {
        return args -> {
            cache.put(1, 100);
            cache.put(2, 1000);
            cache.put(3, 10000);
            cache.put(4, 100000);
        };
    }
}
