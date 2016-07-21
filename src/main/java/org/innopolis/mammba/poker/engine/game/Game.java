package org.innopolis.mammba.poker.engine.game;


import org.innopolis.mammba.poker.engine.Room;
import org.innopolis.mammba.poker.engine.Spectator;
import org.innopolis.mammba.poker.engine.cards.Card;
import org.innopolis.mammba.poker.engine.cards.CardDeck;
import org.innopolis.mammba.poker.engine.combination.Combination;
import org.innopolis.mammba.poker.engine.combination.CombinationsManager;
import org.innopolis.mammba.poker.engine.errors.GameFlowError;
import org.innopolis.mammba.poker.engine.errors.GameFlowErrorType;
import org.innopolis.mammba.poker.engine.errors.GameInitError;
import org.innopolis.mammba.poker.engine.errors.GameInitErrorType;
import org.innopolis.mammba.poker.engine.player.Player;
import org.innopolis.mammba.poker.engine.player.PlayerAction;
import org.innopolis.mammba.poker.engine.player.PlayerActionsEnum;
import org.innopolis.mammba.poker.engine.player.PlayerState;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by anton on 17/07/16.
 *
 */
public class Game {
    private final static Logger LOG = Logger.getLogger("Game");
    private LinkedList<Player> players;
    private CardDeck cardsDeck;
    private LinkedList<Card> openedCards;
    private LinkedList<Round> rounds;
    private int _secret;
    private Round currentRound;
    private int allStackes;
    private GameState state;
    private Room room;


    public Game(Room r){
        cardsDeck = new CardDeck();
        openedCards = new LinkedList<Card>();
        rounds = new LinkedList<Round>();
        _secret = Math.abs((new Date()).hashCode());
        players = new LinkedList<Player>();
        state = GameState.waitForStart;
        room = r;
    }

    public Player addPlayer(Spectator spectator){
        if(rounds.size() > 0 ){
            throw new GameInitError(GameInitErrorType.gameAlreadyStarted, "Game has already started");
        }else if(players.size() > 5){
            throw new GameInitError(GameInitErrorType.tooManyPlayers, "Game has already 5 players");
        }
        Player player = new Player(spectator.getRoom(), this, spectator.getUser(), _secret);
        players.add(player);
        LOG.info("Added player #" + player.getId());

        room.notifySpectators();
        return player;
    }

    private Round createRound(){
        Round newRound = new Round(players, this, _secret);
        rounds.add(newRound);
        currentRound = newRound;
        openedCards.add(cardsDeck.getCard());
        room.notifySpectators();
        return newRound;
    };

    private Round createFirstRound(){
        Round newRound = new Round(players, this, _secret);
        rounds.add(newRound);
        currentRound = newRound;
        openedCards.add(cardsDeck.getCard());
        openedCards.add(cardsDeck.getCard());
        openedCards.add(cardsDeck.getCard());
        room.notifySpectators();
        return newRound;
    };

    public void call(int playerId){
        LOG.info("Player #" + playerId + ": call");
        Player currentPlayer = getPlayerById(playerId);
        checkMoveAbility(currentPlayer);
        currentRound.call(currentPlayer);
        room.notifySpectators();
        checkRoundState();
    }

    public void raise(int playerId, int amount){
        LOG.info("Player #" + playerId + ": raise by " + amount);
        Player currentPlayer = getPlayerById(playerId);
        checkMoveAbility(currentPlayer);
        currentRound.raise(currentPlayer, amount);
        checkRoundState();
        room.notifySpectators();
    }

    public void pass(int playerId){
        LOG.info("Player #" + playerId + ": pass");
        Player currentPlayer = getPlayerById(playerId);
        checkMoveAbility(currentPlayer);
        currentRound.pass(currentPlayer);
        checkRoundState();
        room.notifySpectators();
    }

    public void fold(int playerId){
        LOG.info("Player #" + playerId + ": fold");
        Player currentPlayer = getPlayerById(playerId);
        checkMoveAbility(currentPlayer);
        currentRound.fold(currentPlayer);
        checkRoundState();
        room.notifySpectators();
    }

    public void start(){
        if(rounds.size() > 0){
            throw new GameInitError(GameInitErrorType.gameAlreadyStarted, "Game has been already started");
        }else{
            for(Player player : players){
                if(player.getState() == PlayerState.active){
                    player.setCards(cardsDeck.getCard(), cardsDeck.getCard(), _secret);
                }
            }
            Round newRound = new Round(players, this, _secret);
            rounds.add(newRound);
            currentRound = newRound;
            state = GameState.started;
        }
        LOG.info("Game started");
        room.notifySpectators();
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

    private void checkRoundState(){
        if(currentRound.getRoundState() == RoundState.finished){
            if(rounds.size() == 4){
                state = GameState.finished;
            }else{
                allStackes += currentRound.getAllStakes();
                if(rounds.size() == 1){
                    createFirstRound();
                }else{
                    createRound();
                }
            }

        }
    }

    public GameState getState() {
        return state;
    }

    public int getAllStakes(){
        return allStackes;
    }

    public int getPlayerStake(Player player) {
        if(currentRound == null) return 0;
        return currentRound.getStakeByPlayer(player).getAmount();
    }

    public List<Player> getPlayers(){
        return players;
    }

    public List<Card> getTableCards(){
        return openedCards;
    }

    public List<PlayerAction> getActionsByPlayer(Player player, Integer secret){
        if(secret != _secret){
            throw new GameFlowError(GameFlowErrorType.accessForbidden, "Forbidden");
        }
        LinkedList<PlayerAction> actions = new LinkedList<PlayerAction>();
        if(isPassAvailable(player)){
            actions.add(new PlayerAction(PlayerActionsEnum.pass));
        }else{
            int isCallAmount = isCallAvailable(player);
            if(isCallAmount >= 0){
                actions.add(new PlayerAction(PlayerActionsEnum.call, isCallAmount));
            }
        }
        int isRaiseAmount = isRaiseAvailable(player);
        if(isRaiseAmount >= 0){
            actions.add(new PlayerAction(PlayerActionsEnum.raise, isRaiseAmount));
        }
        actions.add(new PlayerAction(PlayerActionsEnum.fold));
        return actions;
    }

    private boolean isPassAvailable(Player player){
        int stake = currentRound.getStakeByPlayer(player).getAmount();
        return currentRound.getStakeAmount() == stake;
    }

    private int isCallAvailable(Player player){
        int difference = currentRound.getStakeAmount() - currentRound.getStakeByPlayer(player).getAmount();
        if((player.getUser().getBalance() - difference) >= 0){
            return difference;
        }else{
            return -1;
        }
    }

    private int isRaiseAvailable(Player player){
        int difference = currentRound.getStakeAmount() - currentRound.getStakeByPlayer(player).getAmount();
        if((player.getUser().getBalance() - difference) >= 0){
            return (player.getUser().getBalance() - difference);
        }else{
            return -1;
        }
    }

    public Player getWinner(){
        Combination maxCombination = null;
        Player winner = null;

        for(Player player : players){
            if(player.getState() != PlayerState.folded){
                if(winner == null){
                    winner = player;
                    maxCombination = getMaxCombinationByPlayer(player);
                }else{
                    Combination t = getMaxCombinationByPlayer(player);
                    if(t.compareTo(maxCombination) > 0){
                        maxCombination = t;
                        winner = player;
                    }
                }
            }
        }
        return winner;
    }

    public Combination getMaxCombinationByPlayer(Player player){
        LinkedList<Card> table = new LinkedList<Card>(openedCards);

        for(Card playerCard : player.getCards()) {
            table.addLast(playerCard);
        }

        LinkedList<Combination> res = CombinationsManager.getCombinations(
                table.toArray(new Card[0])
        );

        if(res == null) {
            return null;
        }

        return res.getLast();
    }


    /*public getGameState(){

    }*/
}


