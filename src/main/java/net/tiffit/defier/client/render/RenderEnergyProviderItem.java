package net.tiffit.defier.client.render;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.IModelState;
import net.tiffit.defier.client.tesr.EnergyProviderTESR;

public class RenderEnergyProviderItem implements IItemRenderer {

//	private static ResourceLocation iron_block = new ResourceLocation("textures/blocks/iron_block.png");
//	private static ResourceLocation obsidian = new ResourceLocation("textures/blocks/obsidian.png");
	
	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public void renderItem(ItemStack stack, TransformType type) {
		EnergyProviderTESR.render(0, 0, 0, null, 1);
	}
	
	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_ITEM;
	}

}
