package net.tiffit.defier.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.tiffit.defier.Defier;
import net.tiffit.defier.ModItems;
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

@Mod.EventBusSubscriber
public class CommonProxy {
	
    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void blockRegistry(RegistryEvent.Register<Block> e) {
    	IForgeRegistry<Block> reg = e.getRegistry();
    	reg.register(new CompressorBlock());
    	reg.register(new DefierBlock());
    	reg.register(new PatternMolderBlock());
    	reg.register(new EnergyProviderBlock());
    	reg.register(new EnergyProviderModifierBlock(ModifierType.Attack));
    	reg.register(new EnergyProviderModifierBlock(ModifierType.Range));
    	reg.register(new EnergyProviderModifierBlock(ModifierType.Efficiency));
    	reg.register(new EnergyProviderModifierBaseBlack());

    	
    	GameRegistry.registerTileEntity(CompressorTileEntity.class, Defier.MODID + "_compressor");
    	GameRegistry.registerTileEntity(DefierTileEntity.class, Defier.MODID + "_defier");
    	GameRegistry.registerTileEntity(PatternMolderTileEntity.class, Defier.MODID + "_patternmolder");
    	GameRegistry.registerTileEntity(EnergyProviderTileEntity.class, Defier.MODID + "_energyprovider");
    }

    @SubscribeEvent
    public static void itemRegistry(RegistryEvent.Register<Item> e) {
    	IForgeRegistry<Item> reg = e.getRegistry();
    	registerItemBlock(reg, ModItems.compressor);
    	registerItemBlock(reg, ModItems.defier);
    	registerItemBlock(reg, ModItems.patternmolder);
    	registerItemBlock(reg, ModItems.energyprovider);
    	registerItemBlock(reg, ModItems.attackmodifier);
    	registerItemBlock(reg, ModItems.rangemodifier);
    	registerItemBlock(reg, ModItems.efficiencymodifier);
    	registerItemBlock(reg, ModItems.basemodifier);

    	reg.register(new DefierItem("largemass"));
    	reg.register(new DefierItem("defiercore"));
    	reg.register(new DefierItem("defierstar"));
    	reg.register(new EnergyStarItem());
    	reg.register(new DefierItem("strongstar"));
    	reg.register(new DefierItem("speedstar"));
    	reg.register(new PatternItem());
    	reg.register(new BatteryItem("regular", 10_000));
    	reg.register(new BatteryItem("good", 100_000));
    	reg.register(new BatteryItem("great", 1_000_000));
    	reg.register(new BatteryItem("amazing", 1_000_000_000));
    }
    
    private static void registerItemBlock(IForgeRegistry<Item> reg, Block block){
    	reg.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    
}

