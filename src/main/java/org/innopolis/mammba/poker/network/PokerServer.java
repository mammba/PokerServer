package org.innopolis.mammba.poker.network;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import org.innopolis.mammba.poker.engine.*;
import org.innopolis.mammba.poker.engine.errors.InvalidStateError;
import org.innopolis.mammba.poker.engine.player.Player;
import org.innopolis.mammba.poker.network.messages.PlayerActionStateUpdateMessage;
import org.innopolis.mammba.poker.network.messages.data.PlayerActionData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class PokerServer {
    private final static Logger LOG = Logger.getLogger("PokerServer");
    private SocketIOServer      server;
    private HashMap<UUID, User> users = new HashMap<UUID, User>();
    private List<Room>          rooms = new LinkedList<Room>();

    public PokerServer(int port) {
        Configuration config = new Configuration();
        config.getSocketConfig().setReuseAddress(true);
        config.setHostname("localhost");
        config.setPort(port);
        
        // CHECK
        //config.setOrigin("http://localhost");
        //config.setTransports(Transport.WEBSOCKET);

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
        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient client) {
                User pk = new User(client);
                // Set default money amount
                pk.setMoney(1000);

                LOG.info("Client " + client.getSessionId().toString() + " connected");

                // Add user
                users.put(client.getSessionId(), pk);

                // If there is a room with free places, enter it
                for (Room room : rooms) {
                    if (room.hasPlace()) {
                        room.addPlayer(new Spectator(pk, room));
                        return;
                    }
                }

                // Otherwise, create new room and enter it
                Room room = new Room();
                rooms.add(room);
                room.addPlayer(new Spectator(pk, room));
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient client) {
                User user = getUserBySessionID(client.getSessionId());

                LOG.info("Client " + client.getSessionId().toString() + " disconnected");

                // If user spectate some game (or play), delete him from room
                if(user.getSpectator() != null) {
                    user.getSpectator().getRoom().removeUser(user);
                }

                // Remove user session
                users.remove(client.getSessionId());
            }
        });

        server.addEventListener("su", PlayerActionStateUpdateMessage.class, new DataListener<PlayerActionStateUpdateMessage>() {
            public void onData(final SocketIOClient client, PlayerActionStateUpdateMessage msg, final AckRequest ackRequest) {
                User user = getUserBySessionID(client.getSessionId());
                Room room = user.getSpectator().getRoom();
                String ackMessage = "OK";

                LOG.info("Got StateUpdate of type " + msg.messageDataType().toString()
                        + " from client " + client.getSessionId().toString());

                // Check what concrete state update we got from client
                try {
                    switch (msg.messageDataType()) {
                        case PLAYER_ACTION:
                            handlePlayerAction(user, msg);
                            break;
                        default:
                            ackMessage = "Unknown method";
                    }
                    ackRequest.sendAckData(ackMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Error e) {
                    LOG.warning("User " + user.getUUID() + " " + e.toString());
                    ackRequest.sendAckData(e.getMessage());
                }
            }
        });
    }

    private static void handlePlayerAction(User user, PlayerActionStateUpdateMessage msg) {
        Room room = user.getSpectator().getRoom();

        if(room == null) {
            throw new InvalidStateError("user is not in the room");
        }

        if(user.getPlayer() == null) {
            throw new InvalidStateError("user is not assigned to player");
        }

        PlayerActionData action = msg.getData();
        Player player = user.getPlayer();

        if(action.getAction().equals("call")) {
            player.call();
        } else if(action.getAction().equals("raise")) {
            player.raise(action.getStake());
        } else if(action.getAction().equals("pass")) {
            player.pass();
        } else if(action.getAction().equals("check")) {
            player.pass();
        } else if(action.getAction().equals("fold")) {
            player.fold();
        }
    }
}
