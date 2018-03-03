package com.lapots.breed.judge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Level database.
 */
@Entity
@Table(name = "levels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerLevel {
    @Id
    private int level;
    private long exp;
}
