package net.tiffit.defier.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.tiffit.defier.Defier;
import net.tiffit.defier.DefierItems;
import net.tiffit.defier.client.render.RenderDefierItem;
import net.tiffit.defier.client.render.RenderEnergyProviderItem;
import net.tiffit.defier.client.tesr.DefierTESR;
import net.tiffit.defier.client.tesr.EnergyProviderTESR;
import net.tiffit.defier.item.DefierItem;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;
import net.tiffit.tiffitlib.client.ClientRegistryHelper;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);

		ClientRegistry.bindTileEntitySpecialRenderer(DefierTileEntity.class, new DefierTESR());
		ClientRegistry.bindTileEntitySpecialRenderer(EnergyProviderTileEntity.class, new EnergyProviderTESR());
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ClientRegistryHelper.SPECIAL_RENDERS.put(DefierItems.defier, new RenderDefierItem());
		ClientRegistryHelper.SPECIAL_RENDERS.put(DefierItems.energyprovider, new RenderEnergyProviderItem());
		helper.registerModels();
	}

	@SubscribeEvent
	public static void onConfigGuiEdited(OnConfigChangedEvent e) {
		if (e.getModID().equals(Defier.MODID)) {
			Defier.INSTANCE.syncConfig();
		}
	}

}
