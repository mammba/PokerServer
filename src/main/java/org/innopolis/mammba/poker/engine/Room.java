package org.innopolis.mammba.poker.engine;

import org.innopolis.mammba.poker.engine.errors.InvalidStateError;
import org.innopolis.mammba.poker.engine.game.Game;

import java.util.LinkedList;
import java.util.List;

public class Room {
    private static int MAX_PLAYERS = 2;
    private static Long id = 0L;
    private List<Spectator> users = new LinkedList<Spectator>();
    private Game game = new Game(this);
    public Room() {
        id++;
    }

    /**
     * Adds a spectator to a room.
     * @param sp Spectator
     */
    public void addSpectator(Spectator sp) {
        this.users.add(sp);
    }

    /**
     * Adds a player to a room.
     * @param sp Player
     */
    public void addPlayer(Spectator sp) {
        if(this.game.getPlayers().size() >= MAX_PLAYERS) {
            throw new InvalidStateError("Room is full");
        }

        this.users.add(sp);
        game.addPlayer(sp);
        // FIXME: this is only for minimum viable product v.0.0.1
        if (users.size() == MAX_PLAYERS)
            game.start();
    }

    public void removeUser(User user) {
        if(user.getPlayer() != null) {
            user.getPlayer().fold();
        }
        users.remove(user);
    }

    public Game getGame() {
        return game;
    }

    public void notifySpectators() {
        for(Spectator sp:users) {
            sp.notifySpectator();
        }
    }

    public boolean hasPlace() {
        return game.getPlayers().size() < MAX_PLAYERS;
    }
}
