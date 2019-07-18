package server.reaptheflag.reaptheflag.udpserver.Handler.commands;

import server.reaptheflag.reaptheflag.udpserver.network.NetworkUser;
import server.reaptheflag.reaptheflag.udpserver.network.rooms.NetworkRoom;

public interface Command {
    void execute(NetworkUser client, NetworkRoom room);
}
