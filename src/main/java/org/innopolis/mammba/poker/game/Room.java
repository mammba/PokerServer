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
    public void addPlayer(Player sp) {
        this.users.add(sp);
        // FIXME: this is only for minimum viable product v.0.0.1
        if (users.size() == 2)
            game.start();
    }

    public void removeUser(User user) {
        for(Spectator sp:users)
            if (sp.getUser().equals(user)) {
                // TODO: call 'fold' from the game logic if sp is a PLayer
                this.users.remove(sp);
            }
    }
    public void notifySpectators() {
        for(Spectator sp:users) {
            List<Card> myCards = game.getMyCards(sp);
            List<Card> openedCards = game.getOpenedCards(sp);
            List<Stake> stakes = game.getPlayersStakes(sp);
            sp.notifySpectator(myCards, openedCards, stakes);
        }
    }
}
