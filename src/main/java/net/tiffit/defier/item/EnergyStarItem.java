package net.tiffit.defier.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.tiffit.tiffitlib.RegistryHelper.IMultiMeta;
import net.tiffit.tiffitlib.utils.MetaPropertyOverride;

public class EnergyStarItem extends DefierItem implements IMultiMeta{

	public EnergyStarItem() {
		super("energystar");
		setHasSubtypes(true);
		addPropertyOverride(new ResourceLocation("meta"), new MetaPropertyOverride());

	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)){
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
            items.add(new ItemStack(this, 1, 2));
            items.add(new ItemStack(this, 1, 3));
        }
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + stack.getMetadata();
	}

	@Override
	public int getMaxMeta() {
		return 3;
	}
}
