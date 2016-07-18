package org.innopolis.mammba.poker.network.messages.data;

/**
 * Created by mike on 7/18/16.
 */
public class DataRequestData extends AbstractMessageData {
    public DataRequestData() { super(MessageDataType.DATA_REQUEST); }

    private String requestDataType;

    public String getRequestDataType()                       { return requestDataType; }
    public void   setRequestDataType(String requestDataType) { this.requestDataType = requestDataType; }
}
