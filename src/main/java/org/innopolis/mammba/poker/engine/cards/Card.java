package org.innopolis.mammba.poker.engine.cards;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.innopolis.mammba.poker.engine.cards.Suit.*;
public class Card implements Comparable<Card>{
    private Suit suit;
    private Rank rank;

    public Card(Suit nSuit, Rank nRank){
        suit = nSuit;
        rank = nRank;
    }

    public int compareTo(Card card){
        if(this.getPriority() > card.getPriority()){
            return 1;
        }else if(this.getPriority() < card.getPriority()){
            return -1;
        }else{
            return 0;
        }
    }

    public int getPriority(){
        int i = 1;
        for (Rank rank : Rank.values()) {
            if(rank == this.rank){
                break;
            }
            i++;
        }
        return i;
    }

    public Suit getSuit(){
        return suit;
    }

    public String getNotation() {
        return toString();
    }

    public String toString() {
        return suit.toString()+rank.toString();
    }

    public static LinkedList<LinkedList<Card>> devideBySuit(Card[] tableCards, Card[] playerCards){
        LinkedList<LinkedList<Card>> res = new LinkedList<LinkedList<Card>>();
        res.add(new LinkedList<Card>());
        res.add(new LinkedList<Card>());
        res.add(new LinkedList<Card>());
        res.add(new LinkedList<Card>());


        for(Card card : tableCards){
            moveToSuit(card, res);
        }
        for(Card card : playerCards){
            moveToSuit(card, res);
        }
        return res;
    }

    static private void moveToSuit(Card card, LinkedList<LinkedList<Card>> res){
        switch (card.getSuit()){
            case Clubs:
                res.get(0).addLast(card);
                break;
            case Diamonds:
                res.get(1).addLast(card);
                break;
            case Spades:
                res.get(2).addLast(card);
                break;
            case Hearts:
                res.get(3).addLast(card);
                break;
        }
    }

    static public void sortCards(Card[] cards){
        Arrays.sort(cards);
    }

    static public void sortCards(LinkedList<Card> cards){
        Collections.sort(cards);
    }
}


