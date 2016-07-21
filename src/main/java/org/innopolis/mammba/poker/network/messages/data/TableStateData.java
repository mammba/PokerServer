package org.innopolis.mammba.poker.network.messages.data;

import org.innopolis.mammba.poker.engine.cards.*;

public class TableStateData extends AbstractMessageData {
    public TableStateData() {
        super(MessageDataType.TABLE_STATE);
    }

    // Public data (availible to every user)
    private Card[]   tableCards;
    private int      overallStakes;
    private Player[] players;
    private String   gameState;

    // Private data (user-local)
    private int      playerID;
    private String[] actionList;

    public class Player {
        public Player() {}
        private String  name;
        private int     id;
        private int     stake;
        private String  state;
        private Card[]  cards;
        private Combination maxCombination;


        // Getters
        public String  getName()           { return name; }
        public int     getID()             { return id; }
        public int     getStake()          { return stake; }
        public String  getState()          { return state; }
        public Card[]  getCards()          { return cards; }
        public Combination getMaxCombination() {
            return maxCombination;
        }

        // Setters
        public void setName(String name)   { this.name = name; }
        public void setID(int id)          { this.id = id; }
        public void setStake(int stake)    { this.stake = stake; }
        public void setState(String state) { this.state = state; }
        public void setCards(Card[] cards) { this.cards = cards; }
        public void setMaxCombination(Combination maxCombination) {
            this.maxCombination = maxCombination;
        }
    }

    // Getters
    public Card[]   getTableCards()    { return tableCards; }
    public int      getOverallStakes() { return overallStakes; }
    public Player[] getPlayers()       { return players; }
    public int      getPlayerID()      { return playerID; }
    public String[] getActionList()    { return actionList; }
    public String   getGameState()     { return gameState; }

    // Setters
    public void setTableCards(Card[] tableCards)    { this.tableCards = tableCards; }
    public void setOverallStakes(int overallStakes) { this.overallStakes = overallStakes; }
    public void setPlayers(Player[] players)        { this.players = players; }
    public void setPlayerID(int playerID)           { this.playerID = playerID; }
    public void setActionList(String[] actionList)  { this.actionList = actionList; }
    public void setGameState(String gameState)      { this.gameState = gameState; }
}
