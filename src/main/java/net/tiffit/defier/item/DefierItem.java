package net.tiffit.defier.item;

import net.minecraft.item.Item;
import net.tiffit.defier.Defier;

public class DefierItem extends Item {

	public DefierItem(String name){
		setUnlocalizedName(Defier.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(Defier.CTAB);
	}
}
