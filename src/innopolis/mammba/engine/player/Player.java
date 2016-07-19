package innopolis.mammba.engine.player;

import innopolis.mammba.engine.User;
import innopolis.mammba.engine.errors.*;

import innopolis.mammba.engine.cards.Card;
import innopolis.mammba.engine.game.Game;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anton on 17/07/16.
 */


public class Player {
    private int id;
    private User user;
    private PlayerState state;
    private int _secret;
    private Game game;

    private static int idCounter = 0;
    //    Game game;

    List<Card> cards = new LinkedList<>();


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
        // TODO: raise in game
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

