package org.innopolis.mammba.poker.engine;

import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.player.PlayerAction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Spectator {
    private User user;
    private Room room;
    public Spectator(User user, Room room) {
        this.user = user;
        this.room = room;
    }

    public User getUser() {
        return user;
    }
    public void notifyUser() {
        user.notifyUser(room, this);
    }
    public UUID getUUID() {
        return user.getUUID();
    }
    public String getNickname() {
        return user.getNickname();
    }
    public Room getRoom(){
        return room;
    }

    // Placeholder for spectator
    public List<Card> getCards(){
        return new ArrayList<Card>();
    }
    public List<PlayerAction> getActions(){
        return new ArrayList<PlayerAction>();
    }
}
