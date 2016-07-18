package org.innopolis.mammba.poker.game;

import com.corundumstudio.socketio.SocketIOClient;
import org.innopolis.mammba.poker.network.messages.MessageType;
import org.innopolis.mammba.poker.network.messages.TableStateUpdateMessage;
import org.innopolis.mammba.poker.network.messages.data.TableStateData;

import java.util.List;
import java.util.UUID;

public class User {
    private UUID uuid;
    private SocketIOClient client;
    private String nickname;
    private int money;
    private static Long userCounter = 0L;

    /**
     * Default constructor for an anonymous user.
     * Use UserProvider.loadUser() to create a registered user.
     */
    public User(SocketIOClient client) {
        this.client = client;
        this.uuid = client.getSessionId();
        nickname = "Пользователь "+userCounter.toString();
        userCounter++;
    }
    public String getNickname() {
        return nickname;
    }
    public int getMoney() {
        return money;
    }
    public UUID getUUID() {
        return uuid;
    }
    /**
     * We need setters in order to convert JSON to objects via reflection
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public void updateData(Room room, Spectator sp) {
        Game game = room.getGame();
        TableStateUpdateMessage tsum = new TableStateUpdateMessage();
        TableStateData tsd = new TableStateData();

        String[] actions = game.getPossibleActions(sp);
        Card[]   playerCards = (Card[])game.getMyCards(sp).toArray();
        Card[]   tableCards = (Card[])game.getTableCards(sp).toArray();

        List<Player> pl = game.getPlayers();
        TableStateData.Player[] players = new TableStateData.Player[pl.size()];
        for(int i = 0; i < pl.size(); i++) {
            Player p = pl.get(i);
            players[i].setID(p.getUUID().toString());
            players[i].setName(p.getNickname());
            players[i].setStake(game.getStake(sp));
            players[i].setTurn(game.isMyTurn(sp));
        }

        tsd.setActionList(actions);
        tsd.setOverallStakes(game.getOverallStakes());
        tsd.setPlayerCards(playerCards);
        tsd.setTableCards(tableCards);
        tsd.setPlayers(players);

        tsum.setData(tsd);

        client.sendEvent(MessageType.STATE_UPDATE, tsum);
    }
}
