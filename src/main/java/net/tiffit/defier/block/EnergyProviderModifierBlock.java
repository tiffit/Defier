package net.tiffit.defier.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tiffit.defier.Defier;

public class EnergyProviderModifierBlock extends Block {

	public static enum ModifierType{
		Attack("Attacks monsters instead of powering the defier."), 
		Range("Increases the range by 10 blocks."),
		Efficiency("Gives an extra 5% RF to the defier when powering.");
		
		public final String desc;
		
		private ModifierType(String desc) {
			this.desc = desc;
		}
		
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
	
	public final ModifierType modifier;
	
	public EnergyProviderModifierBlock(ModifierType type) {
		super(Material.ROCK);
		setUnlocalizedName(Defier.MODID + ".energyprovidermodifier." + type.toString());
		setRegistryName("energyprovidermodifier." + type.toString());
		setHardness(10.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(Defier.CTAB);
		this.modifier = type;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(modifier.desc);
		super.addInformation(stack, player, tooltip, advanced);
	}

}
