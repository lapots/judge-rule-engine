package com.lapots.breed.judge.domain.rule;

import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.lapots.breed.judge.domain.Player;

/**
 * Level up rule.
 */
@Rule
public class LevelUpRule {
    @Given
    private Player player;

    @Given
    private int closestLevel;

    @Given
    private int maxLevel;

    @When
    public boolean when() {
        return player.getLevel() != closestLevel && player.getLevel() < maxLevel;
    }

    @Then
    public void then() {
        player.setLevel(player.getLevel() + 1);
    }
}
