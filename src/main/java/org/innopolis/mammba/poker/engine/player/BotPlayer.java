package org.innopolis.mammba.poker.engine.player;

import org.innopolis.mammba.poker.engine.Room;
import org.innopolis.mammba.poker.engine.User;
import org.innopolis.mammba.poker.engine.combination.Combination;
import org.innopolis.mammba.poker.engine.game.Game;

import java.util.LinkedList;


public class BotPlayer extends Player {
    public BotPlayer(Room room, Game nGame, User user, int secret) {
        super(room, nGame, user, secret);
    }
    private int counter = 0;

    public void think(){
        Combination combination = game.getMaxCombinationByPlayer(this);
        LinkedList<PlayerAction> actions = new LinkedList<PlayerAction>(getActions());

        switch (counter){
            case 0:
                if(combination.getType().getPriority() > 1){
                    if(getUser().getBalance() > 20){
                        int stake = (int) Math.random();
                        stake = stake%40;
                        if(getUser().getBalance() > stake*2){
                            raise(stake);
                        }
                    }
                }
                break;
            case 1:
                if(combination.getType().getPriority() > 2){
                    for(PlayerAction action : actions){
                        if(action.getType().equals(PlayerActionsEnum.call)){
                            if(action.getAmount() *2  < user.getBalance()){
                                call();
                                break;
                            }else{
                                //TODO: think about it
                                call();
                                break;
                            }
                        }
                    }
                }else if(combination.getType().getPriority() > 4){
                    for(PlayerAction action : actions){
                        if(action.getType().equals(PlayerActionsEnum.call)){
                            if(action.getAmount() > user.getBalance()){
                                call();
                                return;
                            }else{
                                fold();
                            }
                        }
                    }
                    int stake = (int) Math.random();
                    stake = stake%40;
                    if(getUser().getBalance() > stake){
                        raise(stake);
                    }
                }else{
                    pass();
                }
                break;
            case 3:
                if(combination.getType().getPriority() > 5){
                    for(PlayerAction action : actions){
                        if(action.getType().equals(PlayerActionsEnum.call)){
                            if(action.getAmount() > user.getBalance()){
                                call();
                                return;
                            }else{
                                fold();
                            }
                        }
                    }
                }else{
                    pass();
                }
                break;
            case 4:
                if(combination.getType().getPriority() > 6){
                    for(PlayerAction action : actions){
                        if(action.getType().equals(PlayerActionsEnum.call)){
                            if(action.getAmount() > user.getBalance()){
                                call();
                                return;
                            }else{
                                fold();
                            }
                        }
                    }
                }else{
                    pass();
                }
        }
    }

    public void newRound(){
        counter++;
    }
}
