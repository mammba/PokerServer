package org.innopolis.mammba.poker.engine.game;

import org.innopolis.mammba.poker.engine.Spectator;
import org.innopolis.mammba.poker.engine.User;
import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.cards.CardDeck;
import org.innopolis.mammba.poker.engine.errors.GameFlowError;
import org.innopolis.mammba.poker.engine.errors.GameFlowErrorType;
import org.innopolis.mammba.poker.engine.errors.GameInitError;
import org.innopolis.mammba.poker.engine.errors.GameInitErrorType;
import org.innopolis.mammba.poker.engine.player.Player;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private LinkedList<Player> players;
    private CardDeck cardsDeck;
    private LinkedList<Card> openedCards;
    private LinkedList<Round> rounds;
    private int _secret;
    private Round currentRound;

    public Game(LinkedList<User> users){
        checkInitSettings(users);
        initPlayers(users);
        cardsDeck = new CardDeck();
        openedCards = new LinkedList<>();
        rounds = new LinkedList<>();
        _secret = Math.abs((users.toString() + cardsDeck.toString() + new Date()).hashCode());
    }

    public Game(){
        cardsDeck = new CardDeck();
        openedCards = new LinkedList<>();
        rounds = new LinkedList<>();
        _secret = Math.abs((new Date()).hashCode());
        players = new LinkedList<>();
    }

    public Player addUser(User user){
        if(rounds.size() > 0 ){
            throw new GameInitError(GameInitErrorType.gameAlreadyStarted, "Game has already started");
        }else if(players.size() > 5){
            throw new GameInitError(GameInitErrorType.tooManyPlayers, "Game has already 5 players");
        }
        Player player = new Player(user, _secret, this);
        players.add(player);
        return player;
    }

    private void checkInitSettings(LinkedList<User> users){
        if(users.size() < 3){
            throw new GameInitError(GameInitErrorType.notEnoughPlayers, "Not enough players. At least 2");
        }else if(users.size() > 5){
            throw new GameInitError(GameInitErrorType.tooManyPlayers, "Too many players. Max - 5");
        }
    }

    private void initPlayers(LinkedList<User> users){
        players = new LinkedList<>();
        for(User user : users){
            players.add(new Player(user, _secret, this));
        }
    }

    private Round createRound(){
        Round newRound = new Round(players, this, _secret);
        rounds.add(newRound);
        currentRound = newRound;
        return newRound;
    };

    public void call(int playerId, int amount){
        Player currentPlayer = getPlayerById(playerId);
        checkMoveAbility(currentPlayer);
        currentRound.call(currentPlayer, amount);
    }

    public void raise(int playerId, int amount){
        Player currentPlayer = getPlayerById(playerId);
        checkMoveAbility(currentPlayer);
        currentRound.raise(currentPlayer, amount);
    }

    public void pass(int playerId){
        Player currentPlayer = getPlayerById(playerId);
        checkMoveAbility(currentPlayer);
        currentRound.pass(currentPlayer);
    }

    public void fold(int playerId){
        Player currentPlayer = getPlayerById(playerId);
        checkMoveAbility(currentPlayer);
        currentRound.fold(currentPlayer);
    }

    public void start(){
        if(rounds.size() > 0){
            throw new GameInitError(GameInitErrorType.gameAlreadyStarted, "Game has been already started");
        }else{
            createRound();
        }
    }

    private Player getPlayerById(int id){
        for(Player player : players){
            if(player.getId() == id){
                return player;
            }
        }
        throw new GameFlowError(GameFlowErrorType.accessForbidden, "No access to this engine");
    }

    private boolean isMoveAllowed(Player player){
        return player.equals(currentRound.getCurrentPlayer());
    }

    private void checkMoveAbility(Player player) {
        if (!isMoveAllowed(player)) {
            throw new GameFlowError(GameFlowErrorType.notYourTurn, "Not your turn");
        }
    }
    public List<Card> getMyCards(Spectator sp) {
        // TODO
        return null;
    }
    public List<Card> getTableCards(Spectator sp) {
        // TODO
        return null;
    }
    public List<Player> getPlayers() {
        // TODO
        return null;
    }
    public boolean isMyTurn(Spectator sp) {
        // TODO
        return false;
    }
    public int getStake(Spectator sp) {
        // TODO
        return 0;
    }
    public int getOverallStakes() {
        // TODO
        return 0;
    }
    public String[] getPossibleActions(Spectator sp) {
        // TODO
        return new String[]{"fold", "stake"};
    }
}

enum gameMoves{
    call, pass, fold, raise
}
