package net.tiffit.defier.client.render;

import org.lwjgl.opengl.GL11;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.tiffit.defier.Defier;
import net.tiffit.defier.proxy.ClientProxy;

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
		ResourceLocation rL = new ResourceLocation(Defier.MODID + ":textures/blocks/blank.png");
		Minecraft.getMinecraft().getTextureManager().bindTexture(rL);
		GL11.glCallList(ClientProxy.defierSphereIdOutside);
		GL11.glCallList(ClientProxy.defierSphereIdInside);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_ITEM;
	}

}
