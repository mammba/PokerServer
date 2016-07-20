package org.innopolis.mammba.poker.network.messages;

import org.innopolis.mammba.poker.network.messages.data.MessageDataType;

public class StateUpdateMessage extends AbstractMessage {
    StateUpdateMessage(MessageDataType dataType){
        super(MessageType.STATE_UPDATE, dataType);
    }
}
