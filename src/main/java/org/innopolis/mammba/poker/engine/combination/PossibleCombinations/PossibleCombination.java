package org.innopolis.mammba.poker.engine.combination.PossibleCombinations;

import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.combination.Combination;
import org.innopolis.mammba.poker.engine.combination.CombinationType;

import java.util.LinkedList;

/**
 * Created by anton on 20/07/16.
 */
abstract public class PossibleCombination extends Combination {
    protected double probability = 0;
    protected LinkedList<Card> tableCards;
    protected Card[] playerCards;

    protected int playersCounter;
    protected int cardDeckCounter;
    protected int moveCounter;

    PossibleCombination(CombinationType type, Card[] playerCards, int playersCounter) {
        super(type);
        this.tableCards = new LinkedList<Card>();
        this.playerCards = playerCards;
        this.playersCounter = playersCounter;
        moveCounter = 1;
        cardDeckCounter = 50; // 54 - 2*jokers - 2*your cards
        Card.sortCards(playerCards);
    }


    public abstract void calculateProbability();

    public void addCardsToTable(Card card){
        tableCards.add(card);
        cardDeckCounter--;
        Card.sortCards(tableCards);
    }

    public void setPlayersCounter(int counter){
        playersCounter = counter;
    }

    public String toString(){
        String res = "Combination - " + type.toString() + "; Probability - " + probability;
        return res;
    }

    public abstract double nextMove();
    public abstract double doubleNextMove();

}
