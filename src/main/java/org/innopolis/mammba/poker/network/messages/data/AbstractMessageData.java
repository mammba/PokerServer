package org.innopolis.mammba.poker.network.messages.data;

public class AbstractMessageData {
    private final MessageDataType dataType;

    public AbstractMessageData(MessageDataType dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType.toString();
    }
    public void setDataType() { }
}
