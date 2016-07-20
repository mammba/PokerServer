package org.innopolis.mammba.poker.network.messages;

import org.innopolis.mammba.poker.network.messages.data.MessageDataType;
import org.innopolis.mammba.poker.network.messages.data.TableStateData;

public class TableStateUpdateMessage extends StateUpdateMessage {
    private TableStateData data;

    public TableStateUpdateMessage() {
        super(MessageDataType.TABLE_STATE);
    }

    public TableStateData getData()                     { return data; }
    public void           setData(TableStateData data ) { this.data = data; }
}
