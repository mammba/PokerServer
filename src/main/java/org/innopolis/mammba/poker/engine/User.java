package org.innopolis.mammba.poker.engine;

import com.corundumstudio.socketio.SocketIOClient;
import org.innopolis.mammba.poker.engine.game.Game;
import org.innopolis.mammba.poker.engine.game.GameState;
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
        String[] actions  = null;
        int      playerID = -1;

        if(player != null) {
            playerID = player.getId();

            // Get player actions
            List<PlayerAction> actionsList = player.getActions();
            if(actionsList == null) {
                actions = new String[0];
            } else {
                actions = new String[actionsList.size()];
                for (int i = 0; i < actionsList.size(); i++) {
                    actions[i] = actionsList.get(i).toString();
                }
            }
        }

        // Common players' data
        String gameState                = game.getState().toString();
        int    overallStakes            = game.getAllStakes();
        int    winnerID                 = -1;
        Card[] tableCards               = null;
        TableStateData.Player[] players = null;

        // Get table cards
        List<Card> tableCardsList = game.getTableCards();
        if(tableCardsList == null) {
            tableCards = new Card[0];
        } else {
            tableCards = tableCardsList.toArray(new Card[tableCardsList.size()]);
        }

        // Get players info
        List<Player> playersList = game.getPlayers();
        if(playersList == null) {
            players = new TableStateData.Player[0];
        } else {
            players = new TableStateData.Player[playersList.size()];
            for (int i = 0; i < playersList.size(); i++) {
                Player p = playersList.get(i);
                players[i] = tsd.new Player();
                players[i].setName(p.getNickname());
                players[i].setID(p.getId());
                players[i].setStake(game.getPlayerStake(p));
                players[i].setState(p.getState().toString());
                if(game.getState() == GameState.finished || p.equals(player)) {
                    players[i].setCards(getPlayerCards(p));
                    players[i].setMaxCombination(
                            game.getMaxCombinationByPlayer(p)
                    );
                }
            }
        }

        // Get winner
        if(game.getState() == GameState.finished) {
            winnerID = game.getWinner().getId();
        }

        // Fill data object
        tsd.setTableCards(tableCards);
        tsd.setOverallStakes(overallStakes);
        tsd.setPlayers(players);
        tsd.setGameState(gameState);
        tsd.setPlayerID(playerID);
        tsd.setActionList(actions);
        tsd.setWinnerID(winnerID);

        tsum.setData(tsd);

        client.sendEvent(MessageType.STATE_UPDATE, tsum);
    }

    private Card[] getPlayerCards(Player player) {
        if(player == null) {
            return new Card[0];
        }

        Card[] playerCards = null;

        // Get player cards
        List<Card> playerCardsList = player.getCards();
        if(playerCardsList == null) {
            playerCards = new Card[0];
        } else {
            playerCards = new Card[playerCardsList.size()];
            for (int i = 0; i < playerCardsList.size(); i++) {
                playerCards[i] = playerCardsList.get(i);
            }
        }

        return playerCards;
    }
}
