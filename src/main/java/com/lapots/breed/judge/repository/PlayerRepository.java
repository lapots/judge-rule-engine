package com.lapots.breed.judge.repository;

import com.lapots.breed.judge.domain.Player;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface for player repository.
 */
public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
