package com.lapots.breed.judge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Player.
 */
@Entity
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player {
    @Id
    private int id;
    private int level;
    private long experience;
}
