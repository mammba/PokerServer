package org.innopolis.mammba.poker.engine.combination.PossibleCombinations;

import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.combination.Combination;
import org.innopolis.mammba.poker.engine.combination.CombinationType;
import org.innopolis.mammba.poker.engine.combination.CombinationsManager;

/**
 * Created by anton on 20/07/16.
 */
public class PossiblePair extends PossibleCombination {

    private int cardsCounter;
    public PossiblePair(Card[] playerCards, int playersCounter) {
        super(CombinationType.Pair, playerCards, playersCounter);

    }
    @Override
    public void calculateProbability() {
        cardsCounter = 0;
        Card[] arr = new Card[tableCards.size() + 2];
        for(int i = 0; i < arr.length - 2; i++){
            arr[i] = tableCards.get(i);
        }
        arr[arr.length - 2] = playerCards[0];
        arr[arr.length - 1] = playerCards[1];
        Card.sortCards(arr);
        Combination isPair = CombinationsManager.isPair(arr);
        int five = 25;
    }

    @Override
    public double nextMove() {
        return 0;
    }

    @Override
    public double doubleNextMove() {
        return 0;
    }
}
