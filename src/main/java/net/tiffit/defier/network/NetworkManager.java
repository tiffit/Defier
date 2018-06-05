package net.tiffit.defier.network;

import static net.tiffit.tiffitlib.network.NetworkManager.registerMessage;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.tiffit.tiffitlib.network.EventMessageRegister;

@Mod.EventBusSubscriber
public class NetworkManager {

	@SubscribeEvent
	public static void registerMessages(EventMessageRegister e) {
		registerMessage(PacketUpdateRF.Handler.class, PacketUpdateRF.class, Side.CLIENT);
	}

}
