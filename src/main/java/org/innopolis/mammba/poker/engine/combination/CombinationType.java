package org.innopolis.mammba.poker.engine.combination;

/**
 * Created by anton on 20/07/16.
 */
public enum CombinationType {
    Kiker(1), Pair(2), TwoPairs(3), Set(4), Street(5), Flash(6), FullHouse(7), Kare(8), StreetFlash(9), RoyalFlash(10);

    private int priority;

    CombinationType(int priority){
        this.priority = priority;
    }

    public int getPriority(){
        return priority;
    }

}
