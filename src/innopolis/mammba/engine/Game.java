package innopolis.mammba.engine;

import innopolis.mammba.engine.cards.Card;
import innopolis.mammba.engine.cards.CardDeck;
import innopolis.mammba.engine.errors.GameInitError;
import innopolis.mammba.engine.errors.GameInitErrorType;

import java.util.LinkedList;

/**
 * Created by anton on 17/07/16.
 *
 */
public class Game {
    LinkedList<Player> players;
    CardDeck cardsDeck;
    LinkedList<Card> openedCards;



    Game(LinkedList<Player> nPlayers){
        checkInitSettings(nPlayers);
        players = nPlayers;
        cardsDeck = new CardDeck();
    }

    private void checkInitSettings(LinkedList<Player> nPlayers){
        if(nPlayers.size() < 3){
            throw new GameInitError(GameInitErrorType.notEnoughPlayers, "Not enough players. At least 2");
        }else if(nPlayers.size() > 5){
            throw new GameInitError(GameInitErrorType.tooManyPlayers, "Too many players. Max - 5");
        }
    }




}
