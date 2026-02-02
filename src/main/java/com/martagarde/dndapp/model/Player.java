package com.martagarde.dndapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Players")
@Entity
public class Player extends GameEntity{
    @Setter(AccessLevel.NONE)
    @ElementCollection
    @CollectionTable(name = "PlayerInventory")
    @MapKeyColumn(name = "PlayerInventoryItem") // key
    @Column(name = "PlayerInventoryAmount")  // value
    private HashMap<String, Integer> inventory = new HashMap<>(); // make item/object class - probably separate

    public Player (String name, int hp, int atk) {
        setName(name);
        setMaxHp(hp);
        setAtk(atk);
    }

    private void addToInventory (String itemName, int amount) { //TODO replace with item class
        if (inventory.containsKey(itemName)) {
            inventory.replace(itemName, inventory.get(itemName) + amount);
        } else {
            inventory.put(itemName, amount);
        }
    }

    private void removeFromInventory (String itemName, int amount) {
        // TODO throw exceptions if trying to use an item the player doesn't have or trying to use more than the player has
        if (inventory.get(itemName) > amount) {
            inventory.replace(itemName, inventory.get(itemName) - amount);
        } else if (inventory.get(itemName) == amount) {
            inventory.remove(itemName);
        } else {
            // exception for not having enough items
        }
    }

    /*
    private void useItems (HashMap<String, Integer> listOfItems) { // for using multiple items at a time - crafting etc.
        for (String item: listOfItems.keySet()) {
            if (!inventory.containsKey(item)) {
                // TODO throw exception - player doesn't have this item
            } else if (inventory.get(item) < listOfItems.get(item)) {
                // TODO throw exception - player doesn't have enough of this item
            }
        }
        // if everything checks out, remove items from inventory

    }
    */

    private void longRest() {
        setCurrentHp(getMaxHp());
        clearStatusEffects();
    } // TODO short rest

}
