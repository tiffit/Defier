package net.tiffit.defier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DefierRecipe {

	protected final Item item;
	protected final long rfcost;
	
	public DefierRecipe(Item item, long rfcost){
		this.item = item;
		this.rfcost = rfcost;
	}
	
	public DefierRecipe(ResourceLocation itemlocation, long rfcost){
		this.item = Item.REGISTRY.getObject(itemlocation);
		if(item == null)throw new IllegalArgumentException("No item with resource location " + itemlocation.toString());
		this.rfcost = rfcost;
	}
	
	public boolean isValid(ItemStack stack){
		return stack != ItemStack.EMPTY && item == stack.getItem();
	}
	
	public long getCost(){
		return rfcost;
	}
	
	public ItemStack outputItem(){
		return new ItemStack(item);
	}
	
	public Item getItem(){
		return item;
	}
	
}
