package org.innopolis.mammba.poker.game;

import com.corundumstudio.socketio.SocketIOClient;

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
    public void updateData(List<Card> myCards, List<Card> openedCards, List<Stake> stakes) {
        //client.sendEvent("du", new StateUpdateMessage(myCards, openedCards, stakes));
    }
}
