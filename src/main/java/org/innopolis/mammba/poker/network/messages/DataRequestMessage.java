package org.innopolis.mammba.poker.network.messages;
import org.innopolis.mammba.poker.network.messages.data.DataRequestData;
import org.innopolis.mammba.poker.network.messages.data.MessageDataType;

public class DataRequestMessage extends AbstractMessage {
    private DataRequestData data;

    public DataRequestMessage() {
        super(MessageType.DATA_REQUEST, MessageDataType.DATA_REQUEST);
    }

    public DataRequestData getData()          { return data;}
    public void setData(DataRequestData data) { this.data = data; }
}
