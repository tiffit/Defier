package net.tiffit.defier.support.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class DefierRecipeCategory implements IRecipeCategory<DefierRecipeWrapper> {

	public static final String uid = "defier";
	
	private final IDrawable background;
	
	public DefierRecipeCategory(IGuiHelper guiHelper){
		ResourceLocation location = new ResourceLocation("defier", "textures/gui/defier.png");
		background = guiHelper.createDrawable(location, 3, 4, 170, 79);
	}
	
	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return "Defier";
	}

	@Override
	public String getModName() {
		return "Defier";
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public void drawExtras(Minecraft mc) {
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DefierRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 18, 18);
		stacks.init(1, false, 127, 18);
		stacks.set(ingredients);
	}

}
