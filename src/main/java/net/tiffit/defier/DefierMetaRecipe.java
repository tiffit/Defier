package net.tiffit.defier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DefierMetaRecipe extends DefierRecipe{

	protected final int meta;
	
	public DefierMetaRecipe(Item item, int meta, long rfcost){
		super(item, rfcost);
		this.meta = meta;
	}
	
	public DefierMetaRecipe(ResourceLocation itemlocation, int meta, long rfcost){
		super(itemlocation, rfcost);
		this.meta = meta;
	}
	
	public boolean isValid(ItemStack stack){
		return stack != ItemStack.EMPTY && item == stack.getItem() && stack.getMetadata() == meta;
	}
	
	public ItemStack outputItem(){
		return new ItemStack(item, 1, meta);
	}
	
	public int getMeta(){
		return meta;
	}
	
}
