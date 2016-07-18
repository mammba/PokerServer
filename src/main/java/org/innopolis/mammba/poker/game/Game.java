package org.innopolis.mammba.poker.game;

import java.util.List;

public class Game {
    public Game() {
        // Call room.notifySpectators() after every turn
    }
    public void start() {
        // TODO
    }
    public List<Card> getMyCards(Spectator sp) {
        // TODO
        return null;
    }
    public List<Card> getTableCards(Spectator sp) {
        // TODO
        return null;
    }
    public List<Player> getPlayers() {
        // TODO
        return null;
    }
    public boolean isMyTurn(Spectator sp) {
        // TODO
        return false;
    }
    public int getStake(Spectator sp) {
        // TODO
        return 0;
    }
    public int getOverallStakes() {
        // TODO
        return 0;
    }
    public String[] getPossibleActions(Spectator sp) {
        // TODO
        return new String[]{"fold", "stake"};
    }
}
