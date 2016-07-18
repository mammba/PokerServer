package org.innopolis.mammba.poker.network.messages.data;

/**
 * Created by mike on 7/18/16.
 */
public class DataRequestData extends AbstractMessageData {
    public DataRequestData() { super(MessageDataType.DATA_REQUEST); }

    private MessageDataType requestDataType;

    public String getRequestDataType()                     { return requestDataType.toString(); }
    public void setRequestDataType(String requestDataType) { this.requestDataType = MessageDataType.valueOf(requestDataType); }
}
