package org.innopolis.mammba.poker.engine;

import org.innopolis.mammba.poker.engine.errors.InvalidStateError;
import org.innopolis.mammba.poker.engine.game.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Room {
    private final static Logger LOG = Logger.getLogger("Game");
    private static int MAX_PLAYERS = 2;
    private static Long id = 0L;
    private List<Spectator> spectators = new LinkedList<Spectator>();
    private Game game = new Game(this);
    public Room() {
        id++;
    }

    /**
     * Adds a spectator to a room.
     * @param sp Spectator
     */
    public void addSpectator(Spectator sp) {
        this.spectators.add(sp);
    }

    /**
     * Adds a player to a room.
     * @param sp Player
     */
    public void addPlayer(Spectator sp) {
        if(this.game.getPlayers().size() >= MAX_PLAYERS) {
            throw new InvalidStateError("Room is full");
        }
        LOG.info("Add player " + sp.getUser().getUUID());
        this.spectators.add(sp);
        game.addPlayer(sp);
        game.addBot(sp);
        if (!hasPlace()) {
            game.start();
        }
    }

    public void removeUser(User user) {
        if(user.getPlayer() != null) {
            user.getPlayer().fold();
        }
        // FIXME Govnokod
        if(user.getSpectator() != null) {
            for(int i = 0; i < spectators.size(); i++) {
                if(spectators.get(i).equals(user.getSpectator())) {
                    spectators.remove(i);
                }
            }
        }

    }

    public Game getGame() {
        return game;
    }

    public void notifySpectators() {
        for(Spectator sp: spectators) {
            LOG.info("Sending table state update to user " + sp.getUUID());
            sp.notifySpectator();
        }
    }

    public boolean hasPlace() {
        return game.getPlayers().size() < MAX_PLAYERS;
    }
}
