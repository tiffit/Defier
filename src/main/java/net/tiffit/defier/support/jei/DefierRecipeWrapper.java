package net.tiffit.defier.support.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.tiffit.defier.DefierItems;
import net.tiffit.defier.DefierRecipe;
import net.tiffit.tiffitlib.TiffitLib;

public class DefierRecipeWrapper implements IRecipeWrapper {

	private DefierRecipe recipe;
	
	public DefierRecipeWrapper(DefierRecipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ItemStack pattern = new ItemStack(DefierItems.pattern);
		NBTTagCompound tag = new NBTTagCompound();
		ItemStack output = recipe.outputItem();
		tag.setTag("defieritem", output.serializeNBT());
		pattern.setTagCompound(tag);
		ingredients.setInput(ItemStack.class, pattern);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawStringWithShadow("RF: " + TiffitLib.LARGE_NUMBER.format(recipe.getCost()), 14, 43, 0xffffff);
	}

	
	
}
