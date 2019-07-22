package server.reaptheflag.reaptheflag.gameserver.network;

import server.reaptheflag.reaptheflag.gameserver.model.OnlineObject;
import server.reaptheflag.reaptheflag.gameserver.model.OnlinePlayer;
import server.reaptheflag.reaptheflag.gameserver.model.scene.location.Location;
import server.reaptheflag.reaptheflag.gameserver.model.scene.rotation.Rotation;
import server.reaptheflag.reaptheflag.gameserver.network.receivable.ReceivableUdpDataPacket;
/**
 * a higher level wrap of class to manipulate the network requests
 * */
public class NetworkLocationUser extends UdpClientUser implements Configurable{
    public NetworkLocationUser(ReceivableUdpDataPacket packet) {
        super(packet);
    }

    public NetworkLocationUser(UdpClientUser user) {
        super(user.getNetworkPacket());
        setTimeout(user.getTimeout());
    }

    public OnlinePlayer generateModel() {
        OnlinePlayer model = new OnlinePlayer();
        configureModel(model);
        return model;
    }

    @Override
    public void configureModel(OnlineObject obj) {
        OnlinePlayer model = (OnlinePlayer) obj;
        model.setCommandType(1);
        model.setId(getId());
        model.setLocation(new Location());
        model.setRotation(new Rotation());
    }
}