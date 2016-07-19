package org.innopolis.mammba.poker.engine.player;

import org.innopolis.mammba.poker.engine.*;
import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.errors.GameFlowError;
import org.innopolis.mammba.poker.engine.errors.GameFlowErrorType;
import org.innopolis.mammba.poker.engine.game.Game;

import java.util.LinkedList;
import java.util.List;

public class Player extends Spectator {
    private int id;
    private User user;
    private PlayerState state;
    private int _secret;
    private Game game;
    private static int idCounter = 0;

    List<Card> cards = new LinkedList<Card>();
    public Player(User user, Room room) {
        super(user, room);
    }
    public Player(User nUser, int secret, Game nGame){
        user = nUser;
        state = PlayerState.active;
        _secret = secret;
        id = ++idCounter;
        game = nGame;
    }

    public int getId(){
        return id;
    }

    public void call(int stake){
        checkMoveState();
        //TODO: check if user balance enough


    };

    public void pass(){
        checkMoveState();
        game.pass(id);
        state = PlayerState.active;
    }

    public void raise(int stake){
        checkMoveState();
        //TODO: check if user balance enough
        // TODO: raise in engine
        state = PlayerState.active;

    }
    public void fold(){
        checkMoveState();
        game.fold(id);
        state = PlayerState.folded;

    }

    public void changeStateToWaitToMove(int secret){
        if(secret == _secret){
            state = PlayerState.waitForMove;
        }else{
            throw new Error("Invalid key");
        }
    }



    private void checkMoveState(){
        if(!isAllowedToMove()){
            throw new GameFlowError(GameFlowErrorType.notYourTurn, "It isn't your turn to move");
        }
    }

    private boolean isAllowedToMove(){
        return (PlayerState.waitForMove == state);
    }

    private void changeStateToActive(){
        state = PlayerState.active;
    }

    void changeStateToWaitForMove(){
        state = PlayerState.waitForMove;
    }

    public PlayerState getState(){
        return state;
    }

}
