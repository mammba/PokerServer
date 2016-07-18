package org.innopolis.mammba.poker.network.messages;
import org.innopolis.mammba.poker.network.messages.data.DataRequestData;

public class DataRequestMessage extends AbstractMessage {
    private DataRequestData data;

    public DataRequestData getData()          { return data;}
    public void setData(DataRequestData data) { this.data = data; }
}
