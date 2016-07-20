package org.innopolis.mammba.poker.engine;

import org.innopolis.mammba.poker.engine.game.Game;
import org.innopolis.mammba.poker.engine.game.GameState;

import java.util.LinkedList;
import java.util.List;

public class Room {
    private static Long id = 0L;
    private int maxNumber = 6;
    private boolean useBots = false;
    private List<Spectator> users = new LinkedList<Spectator>();
    private Game game = new Game(this);
    public Room() {
        id++;
    }
    public Room(int maxNumber, boolean useBots) {
        id++;
        this.maxNumber = maxNumber;
        this.useBots = useBots;
    }

    /**
     * Returns true if the room is full.
     * @return boolean
     */
    public boolean isFull() {
        return game.getPlayers().size() == maxNumber;
    }

    /**
     * Adds a spectator to a room.
     * @param user User
     * @param room Room
     */
    public Spectator addPlayerOrSpectator(User user, Room room) {
        Spectator sp = new Spectator(user, room);
        if(!isFull())
            sp = game.addPlayer(sp);
        else if (game.getState() == GameState.waitForStart)
            game.start();
        this.users.add(sp);
        return sp;
    }

    public void removeUser(User user) {
        for(Spectator sp:users)
            if (sp.getUser().equals(user)) {
                // TODO: call 'fold' from the engine logic if sp is a PLayer
                this.users.remove(sp);
            }
    }
    public Game getGame() {
        return game;
    }
    public void notifyUsers() {
        for(Spectator sp:users) {
            sp.notifyUser();
        }
    }
}
