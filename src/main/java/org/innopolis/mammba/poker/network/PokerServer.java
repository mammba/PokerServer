package org.innopolis.mammba.poker.network;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

public class PokerServer {
    private SocketIOServer server;
    public PokerServer(int port) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(port);
        // This is needed for localhost testing.
        config.setOrigin("http://localhost");

        server = new SocketIOServer(config);
        addEventListeners();
    }
    public void start() throws InterruptedException {
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }
    private void addEventListeners() {

        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient client) {

            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient client) {

            }
        });

        server.addEventListener("du", StateUpdateMessage.class, new DataListener<StateUpdateMessage>() {
            public void onData(SocketIOClient client, StateUpdateMessage data, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });
    }
}
