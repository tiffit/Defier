package net.tiffit.defier.client.render;

import org.lwjgl.opengl.GL11;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.tiffit.defier.Defier;
import net.tiffit.defier.proxy.ClientProxy;

public class RenderEnergyProviderItem implements IItemRenderer {

	private static ResourceLocation iron_block = new ResourceLocation("textures/blocks/iron_block.png");
	private static ResourceLocation obsidian = new ResourceLocation("textures/blocks/obsidian.png");
	
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
		GL11.glPushMatrix();
		GlStateManager.disableLighting();
		Minecraft.getMinecraft().getTextureManager().bindTexture(iron_block);
		GL11.glColor4d(1, 1, 1, 1);
		BufferBuilder builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		float pixel = 1 / 16F;

		builder.pos(0, pixel * 2, 0).tex(0, 0).endVertex();
		builder.pos(0, pixel * 2, 1).tex(0, 1).endVertex();
		builder.pos(1, pixel * 2, 1).tex(1, 1).endVertex();
		builder.pos(1, pixel * 2, 0).tex(1, 0).endVertex();

		builder.pos(0, 0, 0).tex(0, 0).endVertex();
		builder.pos(1, 0, 0).tex(1, 0).endVertex();
		builder.pos(1, 0, 1).tex(1, 1).endVertex();
		builder.pos(0, 0, 1).tex(0, 1).endVertex();

		builder.pos(0, 0, 0).tex(0, pixel).endVertex();
		builder.pos(0, pixel * 2, 0).tex(0, pixel * 3).endVertex();
		builder.pos(1, pixel * 2, 0).tex(1, pixel * 3).endVertex();
		builder.pos(1, 0, 0).tex(1, pixel).endVertex();

		builder.pos(0, 0, 1).tex(0, pixel).endVertex();
		builder.pos(1, 0, 1).tex(1, pixel).endVertex();
		builder.pos(1, pixel * 2, 1).tex(1, pixel * 3).endVertex();
		builder.pos(0, pixel * 2, 1).tex(0, pixel * 3).endVertex();

		builder.pos(0, 0, 0).tex(0, pixel).endVertex();
		builder.pos(0, 0, 1).tex(1, pixel).endVertex();
		builder.pos(0, pixel * 2, 1).tex(1, pixel * 3).endVertex();
		builder.pos(0, pixel * 2, 0).tex(0, pixel * 3).endVertex();

		builder.pos(1, 0, 0).tex(0, pixel).endVertex();
		builder.pos(1, pixel * 2, 0).tex(0, pixel * 3).endVertex();
		builder.pos(1, pixel * 2, 1).tex(1, pixel * 3).endVertex();
		builder.pos(1, 0, 1).tex(1, pixel).endVertex();

		Tessellator.getInstance().draw();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(obsidian);
		builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		builder.pos(0.5 - pixel, pixel * 2, 0.5 + pixel).tex(pixel, pixel).endVertex();
		builder.pos(0.5 - pixel, pixel * 14, 0.5 + pixel).tex(pixel, pixel * 12).endVertex();
		builder.pos(0.5 - pixel, pixel * 14, 0.5 - pixel).tex(pixel * 3, pixel * 12).endVertex();
		builder.pos(0.5 - pixel, pixel * 2, 0.5 - pixel).tex(pixel * 3, pixel).endVertex();

		builder.pos(0.5 + pixel, pixel * 2, 0.5 + pixel).tex(pixel, pixel).endVertex();
		builder.pos(0.5 + pixel, pixel * 2, 0.5 - pixel).tex(pixel * 3, pixel).endVertex();
		builder.pos(0.5 + pixel, pixel * 14, 0.5 - pixel).tex(pixel * 3, pixel * 12).endVertex();
		builder.pos(0.5 + pixel, pixel * 14, 0.5 + pixel).tex(pixel, pixel * 12).endVertex();

		builder.pos(0.5 + pixel, pixel * 2, 0.5 - pixel).tex(pixel, pixel).endVertex();
		builder.pos(0.5 - pixel, pixel * 2, 0.5 - pixel).tex(pixel * 3, pixel).endVertex();
		builder.pos(0.5 - pixel, pixel * 14, 0.5 - pixel).tex(pixel * 3, pixel * 12).endVertex();
		builder.pos(0.5 + pixel, pixel * 14, 0.5 - pixel).tex(pixel, pixel * 12).endVertex();

		builder.pos(0.5 + pixel, pixel * 2, 0.5 + pixel).tex(pixel, pixel).endVertex();
		builder.pos(0.5 + pixel, pixel * 14, 0.5 + pixel).tex(pixel, pixel * 12).endVertex();
		builder.pos(0.5 - pixel, pixel * 14, 0.5 + pixel).tex(pixel * 3, pixel * 12).endVertex();
		builder.pos(0.5 - pixel, pixel * 2, 0.5 + pixel).tex(pixel * 3, pixel).endVertex();

		Tessellator.getInstance().draw();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, pixel * 14, 0.5);
		GL11.glScalef(0.25f, 0.25f, 0.25f);
		GL11.glColor4d(0.6 + (1 * 0.4), 0.6 - (1 * 0.6), 0.6 - (1 * 0.6), 1);
		ResourceLocation rL = new ResourceLocation(Defier.MODID + ":textures/blocks/blank.png");
		Minecraft.getMinecraft().getTextureManager().bindTexture(rL);
		GL11.glCallList(ClientProxy.defierSphereIdOutside);
		GL11.glCallList(ClientProxy.defierSphereIdInside);
		GL11.glPopMatrix();
		GL11.glColor4d(1, 1, 1, 1);
	}
	
	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_ITEM;
	}

}
