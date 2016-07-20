package org.innopolis.mammba.poker.engine.cards;


public class Card{
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

    public String getNotation() {
        return toString();
    }

    public String toString() {
        return suit.toString()+rank.toString();
    }
}

enum Suit {
    Hearts, Diamonds, Spades, Clubs
}

enum Rank {
    Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace
}

