package org.innopolis.mammba.poker.network;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import org.innopolis.mammba.poker.engine.*;
import org.innopolis.mammba.poker.network.messages.StateUpdateMessage;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.UUID;

public class PokerServer {
    private final static Logger LOG = Logger.getLogger("PokerServer");
    private SocketIOServer server;
    private HashMap<UUID, User> users = new HashMap<UUID, User>();
    RoomManager roomManager = new RoomManager();


    public PokerServer(int port) {
        // For the basic version we'll use only one room
        roomManager.addRoom(new Room());

        Configuration config = new Configuration();
        config.getSocketConfig().setReuseAddress(true);
        config.setHostname("localhost");
        config.setPort(port);
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
     * Stops the server gracefully.
     */
    public void stop() {
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
        /**
         * Handling requests:
         * 1. create room:
         *          Room m = new Room(...);
         *          roomManager.addRoom(Room m);
         *          user.selectRoom(m);
         * 2. select room:
         *          int id = ?; // Got from the request
         *          Room m = roomManager.get(id);
         *          user.selectRoom(Room m);
         * 3. get spectator/player:
         *          user.getPlayerOrSpectator()
         * 4. get user's room:
         *          user.getPlayerOrSpectator().getRoom()
         * 5. get game:
         *          user.getPlayerOrSpectator().getRoom().getGame()
         */
        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient client) {
                User pk = new User(client);
                users.put(client.getSessionId(), pk);
                LOG.info("Client " + client.getSessionId().toString() + " connected");
                boolean joinedTheRoom = false;
                // If there is a not filled room, join it
                for(Room room : roomManager.getRoomList()) {
                    if(!room.isFull()) {
                        pk.setPlayerOrSpectator(room.addPlayerOrSpectator(pk, room));
                        joinedTheRoom = true;
                        break;
                    }
                }

                // Otherwise, create new room and join it
                if (!joinedTheRoom) {
                    Room room = new Room();
                    pk.setPlayerOrSpectator(room.addPlayerOrSpectator(pk, room));
                }
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient client) {
                User user = getUserBySessionID(client.getSessionId());
                if (user.getPlayerOrSpectator() != null)
                    user.getPlayerOrSpectator().getRoom().removeUser(user);
                users.remove(client.getSessionId());
            }
        });

        server.addEventListener("su", StateUpdateMessage.class, new DataListener<StateUpdateMessage>() {
            public void onData(SocketIOClient client, StateUpdateMessage data, AckRequest ackRequest) {
                User user = getUserBySessionID(client.getSessionId());
                System.out.println("got su"+user.getUUID().toString());
            }
        });
    }

}
