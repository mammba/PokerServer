package org.innopolis.mammba.poker.engine;

import  java.util.UUID;

public class Spectator {
    private User user;
    private Room room;
    public Spectator(User user, Room room) {
        this.user = user;
        this.room = room;
    }
    public Spectator() {

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
