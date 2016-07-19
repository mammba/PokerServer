package org.innopolis.mammba.poker.engine.cards;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by anton on 17/07/16.
 * class CardDeck - using for operations with cards(get cards, shuffle, compare, etc);
 *
 */
public class CardDeck {
    private LinkedList<Card> cards;

    public CardDeck(){
        cards = new LinkedList<Card>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        this.shuffle();
    }

    /**
     * shuffle cards deck
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card getCard(){
        return cards.pop();
    }

}

