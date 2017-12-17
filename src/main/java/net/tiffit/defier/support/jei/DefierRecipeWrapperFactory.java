package net.tiffit.defier.support.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.tiffit.defier.DefierRecipe;

public class DefierRecipeWrapperFactory implements IRecipeWrapperFactory<DefierRecipe> {

	@Override
	public IRecipeWrapper getRecipeWrapper(DefierRecipe recipe) {
		return new DefierRecipeWrapper(recipe);
	}

	

}
