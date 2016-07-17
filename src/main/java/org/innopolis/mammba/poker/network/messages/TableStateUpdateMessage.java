package org.innopolis.mammba.poker.network.messages;

import org.innopolis.mammba.poker.network.messages.data.TableStateData;

public class TableStateUpdateMessage extends StateUpdateMessage {
    private TableStateData data;

    public TableStateData getData()                     { return data; }
    public void           setData(TableStateData data ) { this.data = data; }
}
