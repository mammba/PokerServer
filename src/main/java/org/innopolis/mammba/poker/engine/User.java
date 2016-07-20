package org.innopolis.mammba.poker.engine;

import com.corundumstudio.socketio.SocketIOClient;
import org.innopolis.mammba.poker.engine.game.Game;
import org.innopolis.mammba.poker.engine.player.Player;
import org.innopolis.mammba.poker.engine.player.PlayerAction;
import org.innopolis.mammba.poker.network.messages.MessageType;
import org.innopolis.mammba.poker.network.messages.TableStateUpdateMessage;
import org.innopolis.mammba.poker.network.messages.data.TableStateData;
import org.innopolis.mammba.poker.engine.cards.*;

import java.util.List;
import java.util.UUID;

public class User {
    private UUID           uuid;
    private SocketIOClient client;
    private String         nickname;
    private int            money;
    private Spectator      spectator;
    private Player         player;
    private static Long    userCounter = 0L;

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

    public int getBalance() {
        return money;
    }

    public Spectator getSpectator() {
        return spectator;
    }

    public Player getPlayer() {
        return player;
    }

    public void reduceBalance(int amount){
        money -= amount;
    }

    public UUID getUUID() {
        return uuid;
    }


    /**
     * We need setters in order to convert JSON to objects via reflection
     */

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void updateData(Room room, Spectator sp) {
        Game game = room.getGame();
        Player player = sp.getUser().getPlayer();
        TableStateUpdateMessage tsum = new TableStateUpdateMessage();
        TableStateData tsd = new TableStateData();

        // User-specific data
        String[] actions = null;
        Card[]   playerCards = null;

        if(player != null) {
            // Get player actions
            List<PlayerAction> actionsList = player.getActions();
            actions = new String[actionsList.size()];
            for(int i = 0; i < actionsList.size(); i++) {
                actions[i] = actionsList.get(i).toString();
            }

            // Get player cards
            List<Card> playerCardsList = player.getCards();
            playerCards = new Card[playerCardsList.size()];
            for(int i = 0; i < playerCardsList.size(); i++) {
                playerCards[i] = playerCardsList.get(i);
            }
        }

        // Common players' data
        Card[] tableCards = null;
        TableStateData.Player[] players = null;

        // Get table cards
        List<Card> tableCardsList = game.getTableCards();
        tableCards = tableCardsList.toArray(new Card[tableCardsList.size()]);

        // Get players info
        List<Player> playersList = game.getPlayers();
        players = new TableStateData.Player[playersList.size()];
        for(int i = 0; i < playersList.size(); i++) {
            Player p = playersList.get(i);
            players[i].setID(p.getId());
            players[i].setName(p.getNickname());
            players[i].setStake(game.getPlayerStake(player));
            players[i].setState(p.getState().toString());
        }

        // Fill data object
        tsd.setActionList(actions);
        tsd.setOverallStakes(game.getAllStakes());
        tsd.setPlayerCards(playerCards);
        tsd.setTableCards(tableCards);
        tsd.setPlayers(players);


        tsum.setData(tsd);
        client.sendEvent(MessageType.STATE_UPDATE, tsum);
    }
}
