package org.innopolis.mammba.poker.network;

import org.innopolis.mammba.poker.engine.Room;

import java.util.LinkedList;
import java.util.List;

public class RoomManager {
    private List<Room> rooms = new LinkedList<Room>();
    public RoomManager() {

    }

    public void addRoom(Room room) {
        // TODO: timers(?)
        rooms.add(room);
    }

    public void destroyRoom(Room room) {
        rooms.remove(room);
    }

    public List<Room> getRoomList() {
        return rooms;
    }
}
