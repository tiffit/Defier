package net.tiffit.defier.client.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkManager {

    private static int id = 0;

    public static SimpleNetworkWrapper NETWORK = null;
    
    public static int nextID() {
        return id++;
    }

    public static void registerMessages() {
    	NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("ChannelDefier");
    	NETWORK.registerMessage(PacketUpdateRF.Handler.class, PacketUpdateRF.class, nextID(), Side.CLIENT);
    	NETWORK.registerMessage(PacketCreateLightning.Handler.class, PacketCreateLightning.class, nextID(), Side.CLIENT);
    	NETWORK.registerMessage(PacketUpdateContainer.Handler.class, PacketUpdateContainer.class, nextID(), Side.CLIENT);
    }
	
}
