package org.innopolis.mammba.poker.network.messages.data;

public class AbstractMessageData {
    private final String dataType;

    AbstractMessageData(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType() {

    }
}
