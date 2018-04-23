package com.lapots.breed.judge.rest;

import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.rest.facade.api.IPlayerLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest for rule execution.
 */
@RestController
public class PlayerRuleExecutionRest {
    private IPlayerLevelService service;

    /**
     * Constructor injection.
     * @param service calculation service
     */
    @Autowired
    public PlayerRuleExecutionRest(final IPlayerLevelService service) {
        this.service = service;
    }

    /**
     * Increases player level if needed.
     * @param player player
     * @return player
     */
    @PostMapping("/judge/rest/player")
    public Player levelUp(@RequestBody Player player) {
        return service.levelUp(player);
    }
}
