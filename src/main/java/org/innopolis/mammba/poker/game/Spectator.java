package org.innopolis.mammba.poker.game;

import java.util.List;

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
    public void notifySpectator(List<Card> myCards, List<Card> openedCards, List<Stake> stakes) {
        //user.updateData(cards, openedCards, stakes);
    }
}
