package net.tiffit.defier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;

public class DefierRecipeRegistry {

	private static HashMap<DefierRecipe, String> REGISTRY = new HashMap<DefierRecipe, String>();
	
	public static DefierRecipe findRecipeForStack(ItemStack stack){
		for(DefierRecipe rec : REGISTRY.keySet()){
			if(rec.isValid(stack))return rec;
		}
		return null;
	}
	
	public static DefierRecipe registerRecipe(String mod_id, DefierRecipe recipe){
		for(DefierRecipe rec : REGISTRY.keySet()){
			if(ItemStack.areItemsEqual(rec.outputItem(), recipe.outputItem())){
				Defier.logger.info("Mod " + mod_id + " is overritting a defier recipe for " + recipe.getItem().getUnlocalizedName() + " from " + REGISTRY.get(rec));
				REGISTRY.remove(rec);
				break;
			}
		}
		if(recipe.rfcost > 0){
			REGISTRY.put(recipe, mod_id);
		}
		return recipe;
	}
	
	public static List<DefierRecipe> getRecipes(){
		Set<DefierRecipe> recipesSet = ((HashMap<DefierRecipe, String>)REGISTRY.clone()).keySet();
		List<DefierRecipe> recipes = new ArrayList<DefierRecipe>(recipesSet);
		Collections.sort(recipes, new Comparator<DefierRecipe>() {

			@Override
			public int compare(DefierRecipe o1, DefierRecipe o2) {
				long c1 = o1.getCost();
				long c2 = o2.getCost();
				return c1 == c2 ? 0 : c1 < c2 ? -1 : 1;
			}
		});
		return recipes;
	}
	
}
