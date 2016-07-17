package innopolis.mammba.engine;

import innopolis.mammba.engine.errors.*;

import innopolis.mammba.engine.cards.Card;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anton on 17/07/16.
 */


public class Player {
    private User user;
    private PlayerState state;
    private int _secret;
    //    Game game;

    List<Card> cards = new LinkedList<>();


    Player(User nUser, int secret){
        user = nUser;
        state = PlayerState.active;
        _secret = secret;
    }

    public void call(int stake){
        checkMoveState();
        //TODO: check if user balance enough


    };

    public void check(){
        checkMoveState();

    }

    public void raise(int stake){
        checkMoveState();
        //TODO: check if user balance enough
        // TODO: raise in game

        state = PlayerState.active;

    }
    public void pass(){
        checkMoveState();
        state = PlayerState.passed;
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

