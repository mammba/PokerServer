package org.innopolis.mammba.poker;

import org.innopolis.mammba.poker.network.PokerServer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public final static Logger LOG = Logger.getLogger(Main.class.getName());
    public final static int port = 8080;

    public static void main(String[] args) {
        LOG.info("Starting server on port " + port);
        PokerServer server = new PokerServer(port);
        try {
            server.start();
        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOG.info("Shutting down the server");
            server.stop();
        }
    }
}
