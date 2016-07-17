package org.innopolis.mammba.poker.network;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import org.innopolis.mammba.poker.game.*;
import org.innopolis.mammba.poker.network.messages.StateUpdateMessage;
import org.innopolis.mammba.poker.network.messages.TableStateUpdateMessage;
import org.innopolis.mammba.poker.network.messages.data.TableStateData;

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
                TableStateUpdateMessage tsum = new TableStateUpdateMessage();
                TableStateData tsd = new TableStateData();

                String[] actions = {"fold", "check"};
                Card[]   playerCards = new Card[2];
                playerCards[0] = new Card(Card.Suit.Hearts, Card.Rank.Ace);
                playerCards[1] = new Card(Card.Suit.Diamonds, Card.Rank.Ace);

                Card[]   tableCards = new Card[3];
                playerCards[0] = new Card(Card.Suit.Spades, Card.Rank.Eight);
                playerCards[1] = new Card(Card.Suit.Clubs, Card.Rank.Jack);
                playerCards[0] = new Card(Card.Suit.Spades, Card.Rank.Nine);

                TableStateData.Player[] players = new TableStateData.Player[2];
                players[0] = tsd.new Player();
                players[1] = tsd.new Player();

                players[0].setID("id1");
                players[0].setName("Mike");
                players[0].setStake(100);
                players[0].setTurn(true);

                players[1].setID("id2");
                players[1].setName("Bulat");
                players[1].setStake(300);
                players[1].setTurn(false);

                tsd.setActionList(actions);
                tsd.setOverallStakes(1000);
                tsd.setPlayerCards(playerCards);
                tsd.setTableCards(tableCards);
                tsd.setPlayers(players);

                tsum.setData(tsd);

                client.sendEvent("su", tsum);

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
