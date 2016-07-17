package org.innopolis.mammba.poker.game;

import java.util.LinkedList;
import java.util.List;

public class Room {
    private static Long id = 0L;
    private List<Spectator> users = new LinkedList<Spectator>();
    private Game game = new Game();
    public Room() {
        id++;
    }
    public void addUser(Spectator sp) {
        this.users.add(sp);
    }
    public void removeUser(Spectator sp) {
        this.users.remove(sp);
        // TODO: call 'fold' from the game logic
    }
    public void notifySpectators() {
        // TODO
    }
}
