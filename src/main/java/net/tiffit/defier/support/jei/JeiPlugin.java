package net.tiffit.defier.support.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.tiffit.defier.DefierItems;
import net.tiffit.defier.DefierRecipe;
import net.tiffit.defier.DefierRecipeRegistry;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new DefierRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.addRecipes(DefierRecipeRegistry.getRecipes(), DefierRecipeCategory.uid);
		registry.handleRecipes(DefierRecipe.class, new DefierRecipeWrapperFactory(), DefierRecipeCategory.uid);
		registry.addRecipeCatalyst(new ItemStack(DefierItems.defier), DefierRecipeCategory.uid);
	}
}
