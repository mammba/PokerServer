package innopolis.mammba.engine.rounds;

import innopolis.mammba.engine.Game;
import innopolis.mammba.engine.Player;
import innopolis.mammba.engine.PlayerState;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anton on 17/07/16.
 *
 */
public class Round {
    private List<Player> players;
    private List<RoundStake> stakes = new LinkedList<>();
    private Game game;
    private RoundState roundState;
    int _secret;
    int stakeAmount = 0;


    Round(List<Player> nPlayers, Game nGame, int secret){
        players = nPlayers;
        game = nGame;
        roundState = RoundState.waitToMove;
        _secret = secret;
        initStakes();

    }

    void call(Player player, int amount){
        changeStakeAmount(player, amount);
        moveTurnToNextPlayer(player);
    }
    void check(Player player){
        if( (stakeAmount == getStakeByPlayer(player).getAmount()) || (player.getState() == PlayerState.allIn) ){
            moveTurnToNextPlayer(player);
        }else{

        }
    }
    void raise(Player player, int amount){
        stakeAmount += amount;
        changeStakeAmount(player, amount);
        moveTurnToNextPlayer(player);
    }
    void pass(Player player){

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
        for(RoundStake stake : stakes){
            if(stake.getAmount() != stakeAmount){
                return false;
            }
        }
        return true;
    }

}
