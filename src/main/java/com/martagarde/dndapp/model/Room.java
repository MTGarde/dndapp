package com.martagarde.dndapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "RoomsTable")
@Entity
public class Room {

    @Setter(value = AccessLevel.NONE)
    @Column(name = "RoomId")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "RoomTitle") // TODO @Pattern(regexp = "[A-Z]{1}[a-z ]{0,29}") // size min = 1 max = 30?
    private String title;
    @Column(name = "RoomDescription")
    private String description;

    @OneToOne
    @JoinColumn(name = "NRoomId")
    private Room northRoom = null;
    @OneToOne
    @JoinColumn(name = "SRoomId")
    private Room southRoom = null;
    @OneToOne
    @JoinColumn(name = "ERoomId")
    private Room eastRoom = null;
    @OneToOne
    @JoinColumn(name = "WRoomId")
    private Room westRoom = null;

    @OneToOne
    @JoinColumn(name = "EnemyId") // TODO make it so a room can have multiple enemies
    private Enemy enemy;

    @Setter(AccessLevel.NONE)
    @ElementCollection
    @CollectionTable(name = "RoomInventory")
    @MapKeyColumn(name = "RoomInventoryItem") // key
    @Column(name = "RoomInventoryAmount")  // value
    private HashMap<String, Integer> availableItems = new HashMap<>();

    private void setNorthRoom (Room nRoom) {
        if (nRoom != null) {
            northRoom = nRoom;
            nRoom.southRoom = this;
        }
    }

    private void setSouthRoom (Room sRoom) {
        if (sRoom != null) {
            southRoom = sRoom;
            sRoom.northRoom = this;
        }
    }

    private void setEastRoom (Room eRoom) {
        if (eRoom != null) {
            eastRoom = eRoom;
            eRoom.westRoom = this;
        }
    }

    private void setWestRoom (Room wRoom) {
        if (wRoom != null) {
            westRoom = wRoom;
            wRoom.eastRoom = this;
        }
    }

    public Room (String title, String description, Room north, Room south, Room east, Room west, Enemy enemy) {
        setTitle(title);
        setDescription(description);
        setNorthRoom(north);
        setSouthRoom(south);
        setEastRoom(east);
        setWestRoom(west);
        setEnemy(enemy);
    }

    private List<String> getAvailableDirections () {
        List<String> directions = new ArrayList<>();
        if (northRoom != null) directions.add("north");
        if (southRoom != null) directions.add("south");
        if (eastRoom != null) directions.add("east");
        if (westRoom != null) directions.add("west");
        return directions;
    }

    private void addItems (String item, int amount) {
        availableItems.put(item, amount);
    }

}
