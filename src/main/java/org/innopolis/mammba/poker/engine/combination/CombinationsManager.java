package org.innopolis.mammba.poker.engine.combination;

import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.cards.Rank;
import org.innopolis.mammba.poker.engine.cards.Suit;

import java.util.*;

/**
 * Created by anton on 20/07/16.
 */
public class CombinationsManager {

    static protected void sortCards(Card[] cards){
        Arrays.sort(cards);
    }

    static public Combination isPair(Card[] cards){
        Card[] res = new Card[2];
        for(int i = cards.length - 1; i > 0; i--){
            if(cards[i].getPriority() == cards[i-1].getPriority()){
                res[0] = cards[i];
                res[1] = cards[i-1];
                return new Combination(res, CombinationType.Pair);
            }
        }
        return null;
    }

    static public Combination isSet(Card[] cards){
        int counter = 0;
        Card[] res = new Card[3];

        for(int i = cards.length - 1; i > 0; i--){
            if(cards[i].getPriority() == cards[i-1].getPriority()){
                counter++;
                if(counter == 2 ){
                    res[0] = cards[i-1];
                    res[1] = cards[i];
                    res[2] = cards[i+1];
                    return new Combination(res, CombinationType.Set);
                }
            }else{
                counter = 0;
            }
        }
        return null;
    }

    static public Combination isTwoPairs(Card[] cards){
        Card[] res = new Card[4];
        boolean flag = false;

        for(int i = cards.length - 1; i > 0; i--){
            if(cards[i].getPriority() == cards[i-1].getPriority()){

                if(flag){
                    res[2] = cards[i];
                    res[3] = cards[i-1];
                    return new Combination(res, CombinationType.TwoPairs);
                }else{
                    res[0] = cards[i];
                    res[1] = cards[i-1];
                }
                flag = true;
                i--;
            }
        }
        return null;
    }

    static public Combination isFullHouse(Card[] cards){
        if(cards.length < 5){
            return null;
        }
        Combination set = isSet(cards);
        if(set == null){
            return null;
        }
        List<Card> list = new ArrayList<Card>(Arrays.asList(cards));
        for(Card card : set.getCards()){
            list.remove(card);
        }
        cards = list.toArray(new Card[0]);

        Combination pair = isPair(cards);
        if(pair != null){
            list = new ArrayList<Card>(Arrays.asList(set.getCards()));
            list.addAll(new ArrayList<Card>(Arrays.asList(pair.getCards())));
            return new Combination(list.toArray(new Card[0]), CombinationType.FullHouse);
        }

        return null;
    }

    static public Combination isStreet(Card[] cards){
        sortCards(cards);
        Card[] res = new Card[5];
        if(cards.length < 5) return null;
        int counter = 0;
        for(int i = cards.length - 1; i > 0; i--){
            if(cards[i].getPriority() == (cards[i-1].getPriority() + 1)){
                res[counter] = cards[i];
                counter++;
                if(counter == 4){
                    res[counter] = cards[i-1];
                    return new Combination(res, CombinationType.Street);
                }
            }else if(cards[i].getPriority() != (cards[i-1].getPriority())){
                counter = 0;
            }else{

            }
        }
        return null;
    }

    static public Combination isFlash(Card[] cards){
        int counter = 0;
        Card[] res = new Card[5];
        for(Suit suit : Suit.values()){
            counter = 0;
            for(Card card : cards){
                if(card.getSuit().equals(suit)){
                    res[counter] = card;
                    counter++;
                }
                if(counter == 5){
                    return new Combination(res, CombinationType.Flash);
                }
            }

        }
        return null;
    }

    static public Combination isStreetFlash(Card[] cards){
        if(cards.length < 5){
            return null;
        }
        Combination street = isStreet(cards);
        if(street != null){
            Combination flash = isFlash(street.getCards());
            if(flash != null){
                return new Combination(street.getCards(), CombinationType.StreetFlash);
            }
        }
        return null;
    }

    static public Combination isKare(Card[] cards){
        if(cards.length < 4) {
            return null;
        }
        int counter = 0;
        for(int i = 0; i < cards.length-1; i++){
            if(cards[i].getPriority() == cards[i+1].getPriority()){
                counter++;
            }else{
                counter = 0;
            }
            if(counter == 3){
                Card[] res = new Card[4];
                res[0] = cards[i - 2];
                res[1] = cards[i - 1];
                res[2] = cards[i];
                res[3] = cards[i+1];
                return new Combination(res, CombinationType.Kare);
            }
        }
        return null;
    }

    static public Combination isRoyalFlash(Card[] cards){
        if(cards.length < 5) return null;
        Combination streetFlash = isStreetFlash(cards);
        if(streetFlash != null){
            if(streetFlash.getCards()[0].getPriority() == new Card(Suit.Clubs, Rank.Ace).getPriority()){
                return new Combination(streetFlash.getCards(), CombinationType.RoyalFlash);
            }
        }
        return null;
    }

    static public Combination isKiker(Card[] cards){
        Card[] res = new Card[1];
        res[0] = cards[cards.length-1];
        return new Combination(res, CombinationType.Kiker);
    };

    static public LinkedList<Combination> getCombinations(Card[] cards){
        sortCards(cards);
        if(cards.length < 1) return null;

        LinkedList<Combination> res = new LinkedList<Combination>();

        res.addLast(isKiker(cards));
        res.addLast(isPair(cards));
        res.addLast(isTwoPairs(cards));
        res.addLast(isSet(cards));
        res.addLast(isStreet(cards));
        res.addLast(isFlash(cards));
        res.addLast(isFullHouse(cards));
        res.addLast(isStreetFlash(cards));
        res.addLast(isKare(cards));
        res.addLast(isRoyalFlash(cards));

        Iterator<Combination> it = res.listIterator();
        while(it.hasNext()){
            if(it.next() == null){
                it.remove();
            }
        }
        //Collections.reverse(res);
        return res;
    }

}

