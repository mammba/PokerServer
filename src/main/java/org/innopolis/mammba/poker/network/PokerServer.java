package org.innopolis.mammba.poker.network;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import org.innopolis.mammba.poker.game.Player;
import org.innopolis.mammba.poker.game.Room;
import org.innopolis.mammba.poker.game.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class PokerServer {
    private SocketIOServer server;
    private HashMap<UUID, User> users = new HashMap<UUID, User>();
    private List<Room> rooms = new LinkedList<Room>();
    public PokerServer(int port) {
        // For the basic version we'll use only one room
        rooms.add(new Room());

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
                User pk = new User(client);
                users.put(client.getSessionId(), pk);
                // For the basic version we'll use only one room
                rooms.get(0).addPlayer(new Player(pk, rooms.get(0)));
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient client) {
                User user = getUserBySessionID(client.getSessionId());
                // For the basic version we'll use only one room
                // rooms.get(0).removeUser();
                rooms.get(0).removeUser(user);
                users.remove(client.getSessionId());
            }
        });

        server.addEventListener("su", StateUpdateMessage.class, new DataListener<StateUpdateMessage>() {
            public void onData(SocketIOClient client, StateUpdateMessage data, AckRequest ackRequest) {
                User user = getUserBySessionID(client.getSessionId());
                // For 0.0.1 this will just call rooms.get(0).game().* methods
            }
        });
    }
}
