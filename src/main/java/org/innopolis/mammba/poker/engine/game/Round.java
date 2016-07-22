package org.innopolis.mammba.poker.engine.game;

import org.innopolis.mammba.poker.engine.errors.GameFlowError;
import org.innopolis.mammba.poker.engine.errors.GameFlowErrorType;
import org.innopolis.mammba.poker.engine.player.Player;
import org.innopolis.mammba.poker.engine.player.PlayerState;

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
    private int activePlayersCounter;



    Round(LinkedList<Player> nPlayers, Game nGame, int secret){
        players = nPlayers;
        game = nGame;
        roundState = RoundState.waitToMove;
        _secret = secret;
        initStakes();
        chooseFirstPlayerToMove();
        activePlayersCounter = getActivePlayers().size();
    }

    void call(Player player){
        int needToStake = stakeAmount - getStakeByPlayer(player).getAmount();
        if(player.getUser().getBalance() >= needToStake){
            player.getUser().reduceBalance(needToStake);
            changeStakeAmount(player, needToStake);
            movesCounter++;
            moveTurnToNextPlayer(player);
        }else{
            throw new GameFlowError(GameFlowErrorType.incorrectStakeAmountForCallAction, "Incorrect stake amount");
        }
    }

    void pass(Player player){
        if( (stakeAmount == getStakeByPlayer(player).getAmount()) || (player.getState() == PlayerState.allIn) ){
            movesCounter++;
            moveTurnToNextPlayer(player);
        }else{
            throw new GameFlowError(GameFlowErrorType.incorrectAction, "Can't pass when player hasn't enough stakes");
        }
    }

    void raise(Player player, int amount){
        if(player.getUser().getBalance() >= amount) {
            stakeAmount += amount;
            changeStakeAmount(player, amount);
            player.getUser().reduceBalance(amount);
            movesCounter++;
            moveTurnToNextPlayer(player);
        } else {
            throw new GameFlowError(GameFlowErrorType.incorrectAction, "Incorrect stake amount");
        }
    }

    void fold (Player player){
        movesCounter++;
        player.changeStateToFolded(_secret);
        moveTurnToNextPlayer(player);
    }

    private void initStakes(){
        for(Player player : players){
            stakes.add(new RoundStake(player, 0));
        }
    }

    RoundStake getStakeByPlayer(Player nPlayer){
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

    private void moveTurnToNextPlayer(Player oldCurrentPlayer){
        if(isRoundFinished()){
            roundState = RoundState.finished;
        }else{
            boolean flag = false;
            for(int i = 0; i < players.size(); i++){
                Player player = players.get(i);
                if(player.isShouldFold() && player.getState() != PlayerState.folded) {
                    player.changeStateToFolded(_secret);
                }
                if(flag){
                    if( (player.getState() == PlayerState.active) || (player.getState() == PlayerState.allIn)){
                        currentPlayer = player;
                        currentPlayer.changeStateToWaitToMove(_secret);
                        break;
                    }
                }else if(player.equals(oldCurrentPlayer)){
                    flag = true;
                }
                if(i == (players.size() - 1)) i = 0;
            }
        }
    }

    private boolean isRoundFinished(){
        int counter = 0;
        for(Player player : players){
            if(player.getState() != PlayerState.folded){
                counter++;
            }
        }
        if(counter < 2){
            return true;
        }

        if(movesCounter < activePlayersCounter){
            return false;
        }
        for(RoundStake stake : stakes){
            if((stake.getPlayer().getState() == PlayerState.active) && (stake.getAmount() != stakeAmount)){
                return false;
            }
        }
        return true;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public RoundState getRoundState() {
        return roundState;
    }

    public int getAllStakes(){
        int all = 0;
        for(RoundStake stake : stakes){
            all += stake.getAmount();
        }
        return all;
    }

    private void chooseFirstPlayerToMove(){
        for(Player player : players){
            if(player.getState() != PlayerState.folded){
                currentPlayer = player;
                player.changeStateToWaitToMove(_secret);
                return;
            }
        }
        throw new GameFlowError(GameFlowErrorType.noPlayersFoundToPlayInRound, "No non-folded players left");
    }

    private LinkedList<Player> getActivePlayers(){
        LinkedList<Player> activePlayers = new LinkedList<Player>();
        for(Player player : players){
            if(player.getState() != PlayerState.folded){
                activePlayers.add(player);
            }
        }
        return activePlayers;
    }

    int getStakeAmount(){
        return stakeAmount;
    }
}
