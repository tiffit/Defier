package net.tiffit.defier.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class DefierItemStackHandler extends ItemStackHandler{

	public DefierItemStackHandler(int size) {
		super(size);
	}
	
    public ItemStack localInsertItem(int slot, ItemStack stack, boolean simulate) {
    	return super.insertItem(slot, stack, simulate);
    }

}
