package net.tiffit.defier.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.datafix.DataFixer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.tiffit.defier.Defier;
import net.tiffit.defier.block.CompressorBlock;
import net.tiffit.defier.block.DefierBlock;
import net.tiffit.defier.block.EnergyProviderBlock;
import net.tiffit.defier.block.EnergyProviderModifierBaseBlack;
import net.tiffit.defier.block.EnergyProviderModifierBlock;
import net.tiffit.defier.block.EnergyProviderModifierBlock.ModifierType;
import net.tiffit.defier.block.PatternMolderBlock;
import net.tiffit.defier.item.BatteryItem;
import net.tiffit.defier.item.DefierItem;
import net.tiffit.defier.item.EnergyStarItem;
import net.tiffit.defier.item.PatternItem;
import net.tiffit.defier.tileentity.CompressorTileEntity;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;
import net.tiffit.defier.tileentity.PatternMolderTileEntity;
import net.tiffit.tiffitlib.RegistryHelper;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	public static RegistryHelper helper = new RegistryHelper(Defier.MODID);
	
    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void blockRegistry(RegistryEvent.Register<Block> e) {
    	helper.setBlockRegistry(e.getRegistry());
    	
    	helper.register(new CompressorBlock());
    	helper.register(new DefierBlock());
    	helper.register(new PatternMolderBlock());
    	helper.register(new EnergyProviderBlock());
    	helper.register(new EnergyProviderModifierBlock(ModifierType.Attack));
    	helper.register(new EnergyProviderModifierBlock(ModifierType.Range));
    	helper.register(new EnergyProviderModifierBlock(ModifierType.Efficiency));
    	helper.register(new EnergyProviderModifierBaseBlack());

    	
    	helper.registerTileEntity(CompressorTileEntity.class, "compressor");
    	helper.registerTileEntity(DefierTileEntity.class, "_defier");
    	helper.registerTileEntity(PatternMolderTileEntity.class, "patternmolder");
    	helper.registerTileEntity(EnergyProviderTileEntity.class, "energyprovider");
    }

    @SubscribeEvent
    public static void itemRegistry(RegistryEvent.Register<Item> e) {
    	helper.setItemRegistry(e.getRegistry());
    	
    	helper.registerItemBlocks();

    	helper.register(new DefierItem("largemass"));
    	helper.register(new DefierItem("defiercore"));
    	helper.register(new DefierItem("defierstar"));
    	helper.register(new EnergyStarItem());
    	helper.register(new DefierItem("strongstar"));
    	helper.register(new DefierItem("speedstar"));
    	helper.register(new PatternItem());
    	helper.register(new BatteryItem("regular", 10_000));
    	helper.register(new BatteryItem("good", 100_000));
    	helper.register(new BatteryItem("great", 1_000_000));
    	helper.register(new BatteryItem("amazing", 1_000_000_000));
    }    
}

