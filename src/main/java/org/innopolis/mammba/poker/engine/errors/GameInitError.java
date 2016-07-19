package org.innopolis.mammba.poker.engine.errors;

/**
 * Created by anton on 17/07/16.
 *
 */
public class GameInitError extends Error{
    private GameInitErrorType type;
    private String message;

    public GameInitError(GameInitErrorType nType, String nMessage){
        super();
        type = nType;
        message = nMessage;
    }
}

