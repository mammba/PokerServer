package org.innopolis.mammba.poker.network.messages.data;

import org.innopolis.mammba.poker.engine.cards.*;

public class TableStateData extends AbstractMessageData {
    public TableStateData() {
        super(MessageDataType.TABLE_STATE);
    }

    // Public data (available to every user)
    private Card[]   tableCards;
    private int      overallStakes;
    private Player[] players;

    // Private data (user-local)
    private Card[]   playerCards;
    private String[] actionList;

    public class Player {
        public Player() {}
        private String  name;
        private int     id;
        private int     stake;
        private String  state;

        // Getters
        public String  getName()  { return name; }
        public int     getID()    { return id; }
        public int     getStake() { return stake; }
        public String  getState() { return state; }

        // Setters
        public void setName(String name)  { this.name = name; }
        public void setID(int id) { this.id = id; }
        public void setStake(int stake)   { this.stake = stake; }
        public void setState(String state) { this.state = state; }
    }

    // Getters
    public Card[]   getTableCards()    { return tableCards; }
    public int      getOverallStakes() { return overallStakes; }
    public Player[] getPlayers()       { return players; }
    public Card[]   getPlayerCards()   { return playerCards; }
    public String[] getActionList()    { return actionList; }

    // Setters
    public void setTableCards(Card[] tableCards)    { this.tableCards = tableCards; }
    public void setOverallStakes(int overallStakes) { this.overallStakes = overallStakes; }
    public void setPlayers(Player[] players)        { this.players = players; }
    public void setPlayerCards(Card[] playerCards)  { this.playerCards = playerCards; }
    public void setActionList(String[] actionList)  { this.actionList = actionList; }
}
