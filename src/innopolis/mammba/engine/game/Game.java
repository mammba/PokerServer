package innopolis.mammba.engine.game;

import innopolis.mammba.engine.User;
import innopolis.mammba.engine.cards.Card;
import innopolis.mammba.engine.cards.CardDeck;
import innopolis.mammba.engine.errors.GameFlowError;
import innopolis.mammba.engine.errors.GameFlowErrorType;
import innopolis.mammba.engine.errors.GameInitError;
import innopolis.mammba.engine.errors.GameInitErrorType;
import innopolis.mammba.engine.player.Player;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by anton on 17/07/16.
 *
 */
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
        throw new GameFlowError(GameFlowErrorType.accessForbidden, "No access to this game");
    }

    private boolean isMoveAllowed(Player player){
        return player.equals(currentRound.getCurrentPlayer());
    }

    private void checkMoveAbility(Player player){
        if(!isMoveAllowed(player)){
            throw new GameFlowError(GameFlowErrorType.notYourTurn, "Not your turn");
        }
    }
}


enum gameMoves{
    call, pass, fold, raise
}


