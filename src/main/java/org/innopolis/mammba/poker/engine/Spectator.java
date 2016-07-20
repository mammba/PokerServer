package org.innopolis.mammba.poker.engine;

import  java.util.UUID;

public class Spectator {
    private User user;
    private Room room;

    public Spectator(User user, Room room) {
        this.user = user;
        this.room = room;
        user.setSpectator(this);
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
    public Room getRoom(){
        return room;
    }
}
