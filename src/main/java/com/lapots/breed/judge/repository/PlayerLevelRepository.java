package com.lapots.breed.judge.repository;

import com.lapots.breed.judge.domain.PlayerLevel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Player level repository.
 *
 * It is blocking for now (until Java 10 it seems)
 */
public interface PlayerLevelRepository extends CrudRepository<PlayerLevel, Integer> {
    /**
     * Returns closest level.
     * @param exp experience
     * @return player level entity
     */
    @Query("FROM PlayerLevel WHERE exp <= :exp ORDER BY exp DESC")
    List<PlayerLevel> findClosestToExperienceLevel(@Param("exp") long exp);

    /**
     * Returns max level.
     * @return max level entity
     */
    @Query("FROM PlayerLevel ORDER BY level DESC")
    List<PlayerLevel> findMaxLevel();
}
