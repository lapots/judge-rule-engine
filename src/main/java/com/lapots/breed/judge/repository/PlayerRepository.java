package com.lapots.breed.judge.repository;

import com.lapots.breed.judge.domain.Player;
import org.springframework.data.repository.CrudRepository;

/**
 * Player repository.
 */
public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
