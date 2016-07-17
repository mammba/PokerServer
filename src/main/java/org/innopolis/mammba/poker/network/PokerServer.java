package org.innopolis.mammba.poker.network;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import org.innopolis.mammba.poker.network.messages.StateUpdateMessage;
import org.innopolis.mammba.poker.game.User;
import java.util.HashMap;
import java.util.UUID;

public class PokerServer {
    private SocketIOServer server;
    private HashMap<UUID, User> users;
    public PokerServer(int port) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(port);
        // This is needed for localhost testing.
        config.setOrigin("http://localhost");

        server = new SocketIOServer(config);
        addEventListeners();
    }

    /**
     * Starts an infinite loop of processing requests.
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }

    /**
     * Returns a user corresponding to the session id.
     * @param id UUID
     * @return User
     */
    private User getUserBySessionID(UUID id) {
        return users.get(id);
    }

    /**
     * addEventListeners()
     *
     * Defines the client-server poker protocol.
     */
    private void addEventListeners() {
        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient client) {
                User pk = new User();
                users.put(client.getSessionId(), pk);
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient client) {
                users.remove(client.getSessionId());
            }
        });

        server.addEventListener("su", StateUpdateMessage.class, new DataListener<StateUpdateMessage>() {
            public void onData(SocketIOClient client, StateUpdateMessage data, AckRequest ackRequest) {
                User user = getUserBySessionID(client.getSessionId());
            }
        });
    }
}
