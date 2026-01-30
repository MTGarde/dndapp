package com.martagarde.dndapp.model;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@ToString
@MappedSuperclass
public abstract class Entity {
    @Id
    @GeneratedValue
    @Setter(value = AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Max(100)
    @Column(name = "name")
    private String name = "Default";

    @NotNull
    @Min(1)
    @Column(name = "health")
    private int maxHp = 10;

    @Min(0)
    @Column(name = "currenthealth")
    private int currentHp = 10;

    @Min(0)
    @Column(name = "atk")
    private int atk = 3;

    @NotNull
    @Column(name = "shielding")
    private boolean isShielding = false;

    @Column(name = "effects")
    @Setter(value = AccessLevel.NONE)
    private HashMap<String, Integer> statusEffects = new HashMap<>();

    protected void setMaxHp(int hp) {
        this.maxHp = hp;
        this.currentHp = hp;
    }

    public Entity(String name, int maxHp, int atk) {
        setName(name);
        setMaxHp(maxHp);
        setAtk(atk);
    }

    protected void takePhysicalDamage(int amount) {
        if (isShielding) currentHp -= amount/2;
        else currentHp -= amount;
    }

    protected void heal(int amount) {
        currentHp += amount;
        if (currentHp > maxHp) currentHp = maxHp;
    }

    protected void addStatusEffect(String name, int amount) { // amount - how many turns it lasts
        statusEffects.put(name, amount);
    }

    protected void clearStatusEffects() {
        statusEffects.clear();
    }

    protected void executeStatusEffects() {
        for (String e : statusEffects.keySet()) {
            currentHp -= effectDamage.get(e);
            if (statusEffects.get(e) == 1) statusEffects.remove(e); // if last turn, remove status effect
            else statusEffects.replace(e, statusEffects.get(e)-1); // else remove 1 turn
        }
    }

    public static HashMap<String, Integer> effectDamage = new HashMap<>();
    static {
        effectDamage.put("passiveheal", -3); // healing is negative damage
        effectDamage.put("poison", 3);
        effectDamage.put("burn", 5);
    }
}