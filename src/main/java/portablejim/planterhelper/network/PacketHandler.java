package portablejim.planterhelper.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 7/01/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class PacketHandler implements IPacketHandler{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        // No special packets
    }
}
