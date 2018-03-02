package com.lapots.breed.judge.repository;

import com.lapots.breed.judge.domain.PlayerLevel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Player level repository.
 */
public interface PlayerLevelRepository extends CrudRepository<PlayerLevel, Integer> {
    /**
     * Returns closest level.
     * @param exp experience
     * @return player level entity
     */
    @Query("SELECT TOP 1 level FROM levels ORDER BY ABS(exp - :exp)")
    PlayerLevel findClosestToExperienceLevel(long exp);

    /**
     * Returns max level.
     * @return max level entity
     */
    @Query("SELECT MAX(level) FROM levels")
    PlayerLevel findMaxLevel();
}
