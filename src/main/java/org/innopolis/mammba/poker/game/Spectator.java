package org.innopolis.mammba.poker.game;

public class Spectator {
    private User user;
    private Room room;
    public Spectator(User user, Room room) {
        this.user = user;
        this.room = room;
    }
    public void notifySpectator() {
        // TODO
    }
}
