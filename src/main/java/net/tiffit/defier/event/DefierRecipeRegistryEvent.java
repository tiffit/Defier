package net.tiffit.defier.event;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.tiffit.defier.DefierMetaRecipe;
import net.tiffit.defier.DefierRecipe;
import net.tiffit.defier.DefierRecipeRegistry;

public class DefierRecipeRegistryEvent extends Event {
	
   public void registerRecipe(String modid, Item item, long cost){
   	 DefierRecipeRegistry.registerRecipe(modid, new DefierRecipe(item, cost));
   }
   
   public void registerRecipe(String modid, Item item, int meta, long cost){
	   	 DefierRecipeRegistry.registerRecipe(modid, new DefierMetaRecipe(item, meta, cost));
   }
   
   public void registerRecipe(String modid, DefierRecipe recipe){
	   	 DefierRecipeRegistry.registerRecipe(modid, recipe);
   }
	
}
