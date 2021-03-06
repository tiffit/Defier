package net.tiffit.defier;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.tiffit.defier.block.CompressorBlock;
import net.tiffit.defier.block.DefierBlock;
import net.tiffit.defier.block.EnergyProviderBlock;
import net.tiffit.defier.block.EnergyProviderModifierBaseBlack;
import net.tiffit.defier.block.EnergyProviderModifierBlock;
import net.tiffit.defier.block.PatternMolderBlock;
import net.tiffit.defier.item.BatteryItem;
import net.tiffit.defier.item.DefierItem;
import net.tiffit.defier.item.EnergyStarItem;
import net.tiffit.defier.item.PatternItem;

public class DefierItems {

	@GameRegistry.ObjectHolder(Defier.MODID + ":compressor")
	public static CompressorBlock compressor;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":largemass")
	public static DefierItem largemass;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":defiercore")
	public static DefierItem defiercore;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":defier")
	public static DefierBlock defier;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":pattern")
	public static PatternItem pattern;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":patternmolder")
	public static PatternMolderBlock patternmolder;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":energyprovider")
	public static EnergyProviderBlock energyprovider;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":defierstar")
	public static DefierItem defierstar;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":energystar")
	public static EnergyStarItem energystar;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":strongstar")
	public static DefierItem strongstar;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":speedstar")
	public static DefierItem speedstar;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":battery.regular")
	public static BatteryItem batteryregular;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":battery.good")
	public static BatteryItem batterygood;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":battery.great")
	public static BatteryItem batterygreat;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":battery.amazing")
	public static BatteryItem batteryamazing;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":energyprovidermodifier.attack")
	public static EnergyProviderModifierBlock attackmodifier;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":energyprovidermodifier.range")
	public static EnergyProviderModifierBlock rangemodifier;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":energyprovidermodifier.efficiency")
	public static EnergyProviderModifierBlock efficiencymodifier;
	
	@GameRegistry.ObjectHolder(Defier.MODID + ":energyprovidermodifierbase")
	public static EnergyProviderModifierBaseBlack basemodifier;
	
}
