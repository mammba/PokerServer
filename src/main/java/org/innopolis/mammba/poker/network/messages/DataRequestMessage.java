package org.innopolis.mammba.poker.network.messages;
import org.innopolis.mammba.poker.network.messages.data.MessageDataType;

public class DataRequestMessage extends AbstractMessage {
    private MessageDataType data;

    public MessageDataType getData()          { return data;}
    public void setData(MessageDataType data) { this.data = data; }
}
