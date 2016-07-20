package org.innopolis.mammba.poker.network.messages;

import org.innopolis.mammba.poker.network.messages.data.MessageDataType;

public abstract class AbstractMessage {
    private String          messageType;
    private MessageDataType messageDataType;

    AbstractMessage(String mt, MessageDataType mdt) {
        messageType = mt;
        messageDataType = mdt;
    }

    public String messageType() {
        return messageType;
    }

    public MessageDataType messageDataType() {
        return messageDataType;
    }
}
