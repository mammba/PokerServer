package org.innopolis.mammba.poker.engine.player;

import org.innopolis.mammba.poker.engine.*;
import org.innopolis.mammba.poker.engine.errors.*;

import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.game.Game;
import org.innopolis.mammba.poker.engine.game.GameState;

import java.util.LinkedList;
import java.util.List;



public class Player extends Spectator {
    private int         id;
    private PlayerState state;
    private int         _secret;
    private Game        game;
    private boolean     shouldFold;

    private static int idCounter = 0;


    List<Card> cards = new LinkedList<Card>();

    public User getUser(){
        return super.getUser();
    }

    public Player(Room room, Game nGame, User user, int secret){
        super(user, room);
        user.setPlayer(this);
        state = PlayerState.active;
        _secret = secret;
        id = user.getId();
        game = nGame;
    }

    public int getId(){
        return id;
    }

    public void call(){
        if(isGameDied()){
            throw new GameFlowError(GameFlowErrorType.gameFinished, "Game has finished");
        }
        checkMoveState();
        state = PlayerState.active;
        game.call(id);
    }

    public void pass(){
        if(isGameDied()){
            throw new GameFlowError(GameFlowErrorType.gameFinished, "Game has finished");
        }
        checkMoveState();
        state = PlayerState.active;
        game.pass(id);
    }

    public void raise(int stake){
        if(isGameDied()){
            throw new GameFlowError(GameFlowErrorType.gameFinished, "Game has finished");
        }
        checkMoveState();
        if(stake > super.getUser().getBalance()){
            throw new GameFlowError(GameFlowErrorType.noEnoughMoney, "Not enough money");
        }
        state = PlayerState.active;
        game.raise(id, stake);
    }

    public void fold(){
        if(isGameDied()){
            throw new GameFlowError(GameFlowErrorType.gameFinished, "Game has finished");
        }

        if(!isAllowedToMove()) {
            shouldFold = true;
        } else {
            checkMoveState();
            game.fold(id);
        }
    }

    public boolean isShouldFold() {
        return shouldFold;
    }

    public void changeStateToWaitToMove(int secret) {
        if(secret == _secret){
            state = PlayerState.waitForMove;
        }else{
            throw new Error("Invalid key");
        }
    }

    public void changeStateToFolded(int secret) {
        if(secret == _secret){
            state = PlayerState.folded;
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

    public PlayerState getState(){
        return state;
    }

    public void setCards(Card card1, Card card2, int secret){
        if(secret == _secret){
            cards.add(card1);
            cards.add(card2);
        }else{
            throw new Error("Invalid key");
        }
    }

    private boolean isGameDied(){
        return (game.getState() == GameState.finished);
    }

    public List<Card> getCards(){
        return cards;
    }

    public List<PlayerAction> getActions(){
        if(isAllowedToMove()){
            return game.getActionsByPlayer(this, _secret);
        }
        return null;
    }



}



