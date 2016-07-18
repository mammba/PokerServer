package org.innopolis.mammba.poker.game;

// NOTE!!!
// THIS IS TEMPORARY FILE
// REMOVE ON MERGE WITH ANTON

public class Card {
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

    public enum Suit {
        Hearts, Diamonds, Spades, Clubs;
    }

    public enum Rank {
        Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
    }

    public String getNotation() {
        return suit.toString() + rank.toString();
    }
}
