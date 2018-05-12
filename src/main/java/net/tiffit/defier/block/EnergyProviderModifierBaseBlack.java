package net.tiffit.defier.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.tiffit.defier.Defier;
import net.tiffit.defier.block.EnergyProviderModifierBlock.ModifierType;

public class EnergyProviderModifierBaseBlack extends Block {

	public EnergyProviderModifierBaseBlack() {
		super(Material.ROCK);
		setUnlocalizedName(Defier.MODID + ".energyprovidermodifierbase");
		setRegistryName("energyprovidermodifierbase");
		setHardness(10.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(Defier.CTAB);
	}
	
}
