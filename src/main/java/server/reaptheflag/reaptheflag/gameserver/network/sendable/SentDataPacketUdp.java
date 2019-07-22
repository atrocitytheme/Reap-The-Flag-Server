package server.reaptheflag.reaptheflag.gameserver.network.sendable;

import server.reaptheflag.reaptheflag.gameserver.model.OnlineObject;
import server.reaptheflag.reaptheflag.gameserver.network.sendable.converter.JsonFormatConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Packet that's sent to the address, including: packet types, ip, data body
 * */
public class SentDataPacketUdp extends SendableData {
    private List<OnlineObject> dataCollection = new ArrayList<>();
    private static <T extends OnlineObject> SentDataPacketUdp wrap(T obj) {
        return new SentDataPacketUdp();
    }
    public <T extends OnlineObject> void append(T onelineModel) {
        dataCollection.add(onelineModel);
    }

    public SentDataPacketUdp(){}
    public <T extends OnlineObject> SentDataPacketUdp(Collection<T> models) {
        models.forEach((d) -> {
            append(d);
        });
    }

    public String deserilize() {
        return new JsonFormatConverter(dataCollection).convertToSendable();
    }

    @Override
    public byte[] getBytes() {
        return deserilize().getBytes();
    }
}