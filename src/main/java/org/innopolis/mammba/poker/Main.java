package org.innopolis.mammba.poker;

import org.innopolis.mammba.poker.network.PokerServer;

public class Main {
    public static void main(String[] args) {


        PokerServer server = new PokerServer(9092);
        try {
            server.start();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
