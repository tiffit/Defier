package net.tiffit.defier.support.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.tiffit.defier.DefierRecipe;
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.defier.ModItems;

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
		registry.addRecipeCatalyst(new ItemStack(ModItems.defier), DefierRecipeCategory.uid);
		
		registry.addIngredientInfo(new ItemStack(ModItems.largemass), ItemStack.class, "When placed in a compressor, accounts for 1 million items (configurable). Useful to speed up compression but expensive.");
		registry.addIngredientInfo(new ItemStack(ModItems.defiercore), ItemStack.class, "Obtained by placing the max amount of items into a compressor.");
		registry.addIngredientInfo(new ItemStack(ModItems.speedstar), ItemStack.class, "Up to 8 can be placed onto an energy provider to decrease cooldown.");

	}
}
