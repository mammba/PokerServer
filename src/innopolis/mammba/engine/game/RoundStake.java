package innopolis.mammba.engine.game;

import innopolis.mammba.engine.player.Player;

/**
 * Created by anton on 17/07/16.
 *
 */
public class RoundStake {
    private Player player;
    private int amount = 0;


    RoundStake(Player nPlayer, int nAmount){
        player = nPlayer;
        amount = nAmount;
    }

    void up(int adds){
        if(adds > 0){
            amount += adds;
        }
    }

    public Player getPlayer(){
        return player;
    }

    public int getAmount() {
        return amount;
    }
}
