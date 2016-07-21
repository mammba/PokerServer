package org.innopolis.mammba.poker.network.messages.data;

import org.innopolis.mammba.poker.engine.cards.*;
import org.innopolis.mammba.poker.engine.combination.Combination;

public class TableStateData extends AbstractMessageData {
    public TableStateData() {
        super(MessageDataType.TABLE_STATE);
    }

    // Public data (availible to every user)
    private Card[]   tableCards;
    private int      overallStakes;
    private int      roundMaxStake;
    private int      minStake;
    private int      minCoeff;
    private Player[] players;
    private String   gameState;

    // TODO
    // private int      secondsToStart;

    // Private data (user-local)
    private int      playerID;
    private int[]    winnerIDs;
    private String[] actionList;

    public class Player {
        public Player() {}
        private String  name;
        private int     id;
        private int     balance;
        private int     stake;
        private String  state;
        private Card[]  cards;
        private Combination maxCombination;

        // Getters
        public String      getName()           { return name; }
        public int         getID()             { return id; }
        public int         getBalance()        { return balance; }
        public int         getStake()          { return stake; }
        public String      getState()          { return state; }
        public Card[]      getCards()          { return cards; }
        public Combination getMaxCombination() { return maxCombination; }

        // Setters
        public void setName(String name)       { this.name = name; }
        public void setID(int id)              { this.id = id; }
        public void setBalance(int balance)    { this.balance = balance; }
        public void setStake(int stake)        { this.stake = stake; }
        public void setState(String state)     { this.state = state; }
        public void setCards(Card[] cards)     { this.cards = cards; }
        public void setMaxCombination(Combination maxCombination) {
            this.maxCombination = maxCombination;
        }
    }

    // Getters
    public Card[]   getTableCards()    { return tableCards; }
    public int      getOverallStakes() { return overallStakes; }
    public int      getRoundMaxStake() { return roundMaxStake; }
    public int      getMinStake()      { return minStake; }
    public int      getMinCoeff()      { return minCoeff; }
    public Player[] getPlayers()       { return players; }
    public int      getPlayerID()      { return playerID; }
    public String[] getActionList()    { return actionList; }
    public String   getGameState()     { return gameState; }
    public int[]    getWinnerIDs()     { return winnerIDs; }

    // Setters
    public void setTableCards(Card[] tableCards)    { this.tableCards = tableCards; }
    public void setOverallStakes(int overallStakes) { this.overallStakes = overallStakes; }
    public void setRoundMaxStake(int roundMaxStake) { this.roundMaxStake = roundMaxStake; }
    public void setMinStake(int minStake)           { this.minStake = minStake; }
    public void setMinCoeff(int minCoeff)           { this.minCoeff = minCoeff; }
    public void setPlayers(Player[] players)        { this.players = players; }
    public void setPlayerID(int playerID)           { this.playerID = playerID; }
    public void setActionList(String[] actionList)  { this.actionList = actionList; }
    public void setGameState(String gameState)      { this.gameState = gameState; }
    public void setWinnerIDs(int[] winnerIDs)       { this.winnerIDs = winnerIDs; }
}
