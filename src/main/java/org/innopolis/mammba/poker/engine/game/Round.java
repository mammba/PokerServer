package org.innopolis.mammba.poker.engine.game;

import org.innopolis.mammba.poker.engine.errors.GameFlowError;
import org.innopolis.mammba.poker.engine.errors.GameFlowErrorType;
import org.innopolis.mammba.poker.engine.player.*;
import org.innopolis.mammba.poker.engine.game.Game;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anton on 17/07/16.
 *
 */
class Round {
    private List<Player> players;
    private Player currentPlayer;
    private List<RoundStake> stakes = new LinkedList<RoundStake>();
    private Game game;
    private RoundState roundState;
    private int _secret;
    private int stakeAmount = 0;
    private int movesCounter = 0;

    Round(List<Player> nPlayers, Game nGame, int secret){
        players = nPlayers;
        game = nGame;
        roundState = RoundState.waitToMove;
        _secret = secret;
        initStakes();
        nPlayers.get(0).changeStateToWaitToMove(secret);
        currentPlayer = nPlayers.get(0);
    }

    void call(Player player, int amount){
        changeStakeAmount(player, amount);
        moveTurnToNextPlayer(player);
        movesCounter++;
    }

    void pass(Player player){
        if( (stakeAmount == getStakeByPlayer(player).getAmount()) || (player.getState() == PlayerState.allIn) ){
            movesCounter++;
            moveTurnToNextPlayer(player);
        }else{
            throw new GameFlowError(GameFlowErrorType.incorrectAction, "Can't pass when player hasn't eniugh stakes");
        }
    }

    void raise(Player player, int amount){
        stakeAmount += amount;
        changeStakeAmount(player, amount);
        moveTurnToNextPlayer(player);
    }

    void fold (Player player){
        movesCounter++;
        moveTurnToNextPlayer(player);
    }

    private void initStakes(){
        for(Player player : players){
            stakes.add(new RoundStake(player, 0));
        }
    }

    private RoundStake getStakeByPlayer(Player nPlayer){
        for(RoundStake stake : stakes){
            if(stake.getPlayer() == nPlayer){
                return stake;
            }
        }
        return null;
    }

    private void changeStakeAmount(Player nPlayer, int adds){
        RoundStake stake = getStakeByPlayer(nPlayer);
        stake.up(adds);
    }

    private void moveTurnToNextPlayer(Player currentPlayer){
        if(isRoundFinished()){
            roundState = RoundState.finished;
        }else{
            boolean flag = false;
            for(Player player : players){
                if(flag){
                    if( (player.getState() == PlayerState.active) || (player.getState() == PlayerState.allIn)){
                        currentPlayer = player;
                        player.changeStateToWaitToMove(_secret);
                        break;
                    }
                }else if(player.equals(currentPlayer)){
                    flag = true;
                }

            }
        }
    }

    private boolean isRoundFinished(){
        if(movesCounter < players.size()){
            return false;
        }
        for(RoundStake stake : stakes){
            if(stake.getAmount() != stakeAmount){
                return false;
            }
        }
        return true;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
