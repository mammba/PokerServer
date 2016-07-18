package org.innopolis.mammba.poker.game;

import java.util.List;
import  java.util.UUID;

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
    public void notifySpectator() {
        user.updateData(room, this);
    }
    public UUID getUUID() {
        return user.getUUID();
    }
    public String getNickname() {
        return user.getNickname();
    }
}
