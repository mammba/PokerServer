package org.innopolis.mammba.poker.network.messages.data;

public class PlayerActionData extends AbstractMessageData {
    PlayerActionData() {
        super(MessageDataType.PLAYER_ACTION);
    }

    private String action;
    private int    stake;

    public String getAction() { return action; }
    public int    getStake()  { return stake;  }

    public void setAction(String action) { this.action = action; }
    public void setStake(int stake)      { this.stake = stake;   }
}
