package com.martagarde.dndapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Enemies")
@Entity
public class Enemy extends GameEntity {

    public Enemy (String name, int hp, int atk) {
        setName(name);
        setMaxHp(hp);
        setAtk(atk);
    }

}
