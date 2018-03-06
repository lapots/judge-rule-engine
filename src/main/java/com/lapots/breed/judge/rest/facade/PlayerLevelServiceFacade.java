package com.lapots.breed.judge.rest.facade;

import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.repository.PlayerRepository;
import com.lapots.breed.judge.rest.exception.IncorrectRequestContentException;
import com.lapots.breed.judge.rest.facade.api.IPlayerLevelService;
import com.lapots.breed.judge.service.rule.api.IRuleExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link IPlayerLevelService}.
 */
@Service
public class PlayerLevelServiceFacade implements IPlayerLevelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerLevelServiceFacade.class);
    private IRuleExecutionService ruleExecutionService;
    private PlayerRepository playerRepository;

    /**
     * Constructor injection.
     * @param ruleExecutionService rule execution service
     * @param playerRepository player repository
     */
    @Autowired
    public PlayerLevelServiceFacade(final IRuleExecutionService ruleExecutionService,
                                    final PlayerRepository playerRepository) {
        this.ruleExecutionService = ruleExecutionService;
        this.playerRepository = playerRepository;
    }

    @Override
    public Player levelUp(Player player) {
        Optional<Player> foundPlayer = playerRepository.findById(player.getId());
        LOGGER.debug("Found player: [{}]", foundPlayer);
        if (0 == player.getId() || !playerRepository.findById(player.getId()).isPresent()) {
            String msg = String.format("Player with id [%s] doesn't exist.", player.getId());
            LOGGER.error(msg);
            throw new IncorrectRequestContentException(msg);
        } else {
            return ruleExecutionService.levelUpPlayer(player);
        }
    }
}
