package org.innopolis.mammba.poker.network.messages;

import org.innopolis.mammba.poker.network.messages.data.PlayerActionData;

public class PlayerActionStateUpdateMessage extends StateUpdateMessage {
    private PlayerActionData data;

    public PlayerActionData getData()                      { return data; }
    public void             setData(PlayerActionData data) { this.data = data; }
}
