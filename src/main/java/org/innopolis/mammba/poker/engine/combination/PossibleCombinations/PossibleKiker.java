package org.innopolis.mammba.poker.engine.combination.PossibleCombinations;

import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.cards.Rank;
import org.innopolis.mammba.poker.engine.cards.Suit;
import org.innopolis.mammba.poker.engine.combination.CombinationType;

import java.util.LinkedList;

/**
 * Created by anton on 20/07/16.
 */
public class PossibleKiker extends PossibleCombination {

    private int cardsCounter = 0;
    public PossibleKiker(Card[] playerCards, int playersCounter) {
        super(CombinationType.Kiker, playerCards, playersCounter);

    }
    public void calculateProbability() {
        cardsCounter = 0;
        if (playerCards[1].getPriority() <= tableCards.getLast().getPriority()) {
            probability = 0;
        } else {
            LinkedList<LinkedList<Card>> res = Card.devideBySuit(tableCards.toArray(new Card[0]), playerCards);
            for (LinkedList<Card> suitList : res) {
                if (suitList.size() == 0) {
                    cardsCounter += new Card(Suit.Clubs, Rank.Ace).getPriority() - playerCards[1].getPriority() + 1;
                } else if (suitList.get(suitList.size() - 1).getPriority() <= playerCards[1].getPriority()) {
                    if (suitList.get(suitList.size() - 1).equals(playerCards[1])) {
                        cardsCounter += new Card(Suit.Clubs, Rank.Ace).getPriority() - playerCards[1].getPriority();
                    }else  if (suitList.get(suitList.size() - 1).equals(playerCards[0])) {
                        cardsCounter += new Card(Suit.Clubs, Rank.Ace).getPriority() - playerCards[0].getPriority();
                    }else {
                        cardsCounter += new Card(Suit.Clubs, Rank.Ace).getPriority() - playerCards[1].getPriority() + 1;
                    }
                }
            }
            int t = playersCounter * 2;
            double prob = 1;
            while (t > 0) {
                prob = prob * nextMoveProbability();
                cardDeckCounter--;
                t--;
            }
            probability = prob;
        }
    }

    private double nextMoveProbability(){
        double res = (cardDeckCounter - cardsCounter);
        return res/cardDeckCounter;
    }
    public double nextMove(){
        return probability * nextMoveProbability();
    }

    public double doubleNextMove(){
        return probability * nextMoveProbability() * (cardDeckCounter - cardsCounter - 1) / (cardDeckCounter - 1);
    }


}
