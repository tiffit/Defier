package net.tiffit.defier.client.render;

import org.lwjgl.opengl.GL11;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.IModelState;
import net.tiffit.tiffitlib.utils.RenderUtils;

public class RenderDefierItem implements IItemRenderer {

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public void renderItem(ItemStack stack, TransformType transformType) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, .5, .5);
		GL11.glScalef(0.7F, 0.7F, 0.7F);
		GL11.glColor4f(0f, 0f, 0f, 1f);
		RenderUtils.renderCircle();
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_ITEM;
	}

}
