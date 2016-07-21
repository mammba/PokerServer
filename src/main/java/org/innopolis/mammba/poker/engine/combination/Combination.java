package org.innopolis.mammba.poker.engine.combination;

import org.innopolis.mammba.poker.engine.cards.Card;

/**
 * Created by anton on 20/07/16.
 */
public class Combination implements Comparable<Combination>{
    private Card[] cards;
    protected CombinationType type;

    Combination(Card[] cards, CombinationType type){
        this.cards = cards;
        this.type = type;
    }
    protected Combination(CombinationType type){
        this.type = type;
    }


    public int compareTo(Combination o) {
        return (this.type.getPriority() - o.type.getPriority());
    }

    public Card[] getCards(){
        return cards;
    }

    public String toString(){
        String res = "Combination - " + type.toString() + "\n";
        for(Card card : cards){
            res += "   " + card.toString() + "\n";
        }
        return res;
    }
}



