package com.lapots.breed.judge.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Player.
 */
@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    private int id;
    private int level;
    private long experience;
}
