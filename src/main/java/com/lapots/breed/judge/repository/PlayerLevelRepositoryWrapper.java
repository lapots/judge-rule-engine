package com.lapots.breed.judge.repository;

import com.lapots.breed.judge.domain.PlayerLevel;
import com.lapots.breed.judge.repository.api.IPlayerLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link IPlayerLevelRepository}.
 */
@Repository
public class PlayerLevelRepositoryWrapper implements IPlayerLevelRepository {

    @Autowired
    private PlayerLevelRepository jpaRepository;

    @Override
    public PlayerLevel findClosestToExperienceLevel(long exp) {
        return jpaRepository.findClosestToExperienceLevel(exp).get(0);
    }

    @Override
    public PlayerLevel findMaxLevel() {
        return jpaRepository.findMaxLevel().get(0);
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    @Override
    public void save(final PlayerLevel level) {
        jpaRepository.save(level);
    }
}
