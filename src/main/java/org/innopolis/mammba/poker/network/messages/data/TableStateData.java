package org.innopolis.mammba.poker.network.messages.data;

import org.innopolis.mammba.poker.game.Card;

public class TableStateData extends AbstractMessageData {
    public TableStateData() {
        super(MessageDataType.TABLE_STATE);
    }

    // Public data (availible to every user)
    private Card[]   tableCards;
    private int      overallStakes;
    private Player[] players;

    // Private data (user-local)
    private Card[]   playerCards;
    private String[] actionList;

    public class Player {
        public Player() {}
        private String  name;
        private String  id;
        private int     stake;
        private boolean turn;

        // Getters
        public String getName()  { return name; }
        public String getID()    { return id; }
        public int getStake()    { return stake; }
        public boolean getTurn() { return turn; }

        // Setters
        public void setName(String name)  { this.name = name; }
        public void setID(String id)      { this.id = id; }
        public void setStake(int stake)   { this.stake = stake; }
        public void setTurn(boolean turn) { this.turn = turn; }
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
