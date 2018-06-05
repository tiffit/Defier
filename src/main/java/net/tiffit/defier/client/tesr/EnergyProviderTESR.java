package net.tiffit.defier.client.tesr;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.tiffit.defier.block.EnergyProviderModifierBlock;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;
import net.tiffit.tiffitlib.utils.RenderUtils;

public class EnergyProviderTESR extends TileEntitySpecialRenderer<EnergyProviderTileEntity> {

	private static ResourceLocation obsidian = new ResourceLocation("textures/blocks/obsidian.png");
	private static float pixel = 1 / 16F;
	
	@Override
	public void render(EnergyProviderTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		render(x, y, z, te.getWorld().getBlockState(te.getPos().down()), te.rf.getEnergyStored() / (double) te.rf.getMaxEnergyStored());
	}

	public static void render(double x, double y, double z, IBlockState belowBlock, double percent){
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.disableLighting();
		bindTextureS(TextureMap.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite sprite = null;
		if(belowBlock != null && belowBlock.getBlock() instanceof EnergyProviderModifierBlock){
			sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(belowBlock).getParticleTexture();
		}else{
			sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.IRON_BLOCK.getDefaultState()).getParticleTexture();
		}
		GlStateManager.color(1, 1, 1, 1);
		BufferBuilder builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		
		float pixelBelow = (sprite.getMaxU()-sprite.getMinU())/16;

		builder.pos(0, pixel * 2, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		builder.pos(0, pixel * 2, 1).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		builder.pos(1, pixel * 2, 1).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
		builder.pos(1, pixel * 2, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();

		builder.pos(0, 0, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		builder.pos(1, 0, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		builder.pos(1, 0, 1).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
		builder.pos(0, 0, 1).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();

		builder.pos(0, 0, 0).tex(sprite.getMinU(), sprite.getMinV() + pixelBelow).endVertex();
		builder.pos(0, pixel * 2, 0).tex(sprite.getMinU(), sprite.getMinV() + pixelBelow * 3).endVertex();
		builder.pos(1, pixel * 2, 0).tex(sprite.getMaxU(), sprite.getMinV() + pixelBelow * 3).endVertex();
		builder.pos(1, 0, 0).tex(sprite.getMaxU(), sprite.getMinV() + pixelBelow).endVertex();

		builder.pos(0, 0, 1).tex(sprite.getMinU(), sprite.getMinV()+pixelBelow).endVertex();
		builder.pos(1, 0, 1).tex(sprite.getMaxU(), sprite.getMinV()+pixelBelow).endVertex();
		builder.pos(1, pixel * 2, 1).tex(sprite.getMaxU(), sprite.getMinV()+pixelBelow * 3).endVertex();
		builder.pos(0, pixel * 2, 1).tex(sprite.getMinU(), sprite.getMinV()+pixelBelow * 3).endVertex();

		builder.pos(0, 0, 0).tex(sprite.getMinU(), sprite.getMinV()+pixelBelow).endVertex();
		builder.pos(0, 0, 1).tex(sprite.getMaxU(), sprite.getMinV()+pixelBelow).endVertex();
		builder.pos(0, pixel * 2, 1).tex(sprite.getMaxU(), sprite.getMinV()+pixelBelow * 3).endVertex();
		builder.pos(0, pixel * 2, 0).tex(sprite.getMinU(), sprite.getMinV()+pixelBelow * 3).endVertex();

		builder.pos(1, 0, 0).tex(sprite.getMinU(), sprite.getMinV()+pixelBelow).endVertex();
		builder.pos(1, pixel * 2, 0).tex(sprite.getMinU(), sprite.getMinV()+pixelBelow * 3).endVertex();
		builder.pos(1, pixel * 2, 1).tex(sprite.getMaxU(), sprite.getMinV()+pixelBelow * 3).endVertex();
		builder.pos(1, 0, 1).tex(sprite.getMaxU(), sprite.getMinV()+pixelBelow).endVertex();
		Tessellator.getInstance().draw();
		GlStateManager.popMatrix();

		renderPole(x+pixel*2.5, y, z+pixel*2.5, 25, 1f, 0f, -1f);
		renderPole(x+pixel*2.5, y, z+pixel*13.5, 25, -1f, 0f, -1f);
		renderPole(x+pixel*13.5, y, z+pixel*13.5, 25, -1f, 0f, 1f);
		renderPole(x+pixel*13.5, y, z+pixel*2.5, 25, 1f, 0f, 1f);


		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + pixel * 14, z + 0.5);
		GlStateManager.scale(0.25f, 0.25f, 0.25f);
		GlStateManager.color((float)(0.6f + (percent * 0.4f)), (float)(0.6f - (percent * 0.6f)), (float)(0.6f - (percent * 0.6f)), 1);
		RenderUtils.renderCircle();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.popMatrix();
	}
	
	private static void renderPole(double x, double y, double z, float angle, float rotX, float rotY, float rotZ){
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(angle, rotX, rotY, rotZ);
		bindTextureS(obsidian);
		BufferBuilder builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		int height = 12;
		
		builder.pos( - pixel, pixel * 2,  + pixel).tex(pixel, pixel).endVertex();
		builder.pos( - pixel, pixel * height,  + pixel).tex(pixel, pixel * 12).endVertex();
		builder.pos( - pixel, pixel * height,  - pixel).tex(pixel * 3, pixel * 12).endVertex();
		builder.pos( - pixel, pixel * 2,  - pixel).tex(pixel * 3, pixel).endVertex();

		builder.pos( + pixel, pixel * 2,  + pixel).tex(pixel, pixel).endVertex();
		builder.pos( + pixel, pixel * 2,  - pixel).tex(pixel * 3, pixel).endVertex();
		builder.pos( + pixel, pixel * height,  - pixel).tex(pixel * 3, pixel * 12).endVertex();
		builder.pos( + pixel, pixel * height,  + pixel).tex(pixel, pixel * 12).endVertex();

		builder.pos( + pixel, pixel * 2,  - pixel).tex(pixel, pixel).endVertex();
		builder.pos( - pixel, pixel * 2,  - pixel).tex(pixel * 3, pixel).endVertex();
		builder.pos( - pixel, pixel * height,  - pixel).tex(pixel * 3, pixel * 12).endVertex();
		builder.pos( + pixel, pixel * height,  - pixel).tex(pixel, pixel * 12).endVertex();

		builder.pos( + pixel, pixel * 2,  + pixel).tex(pixel, pixel).endVertex();
		builder.pos( + pixel, pixel * height,  + pixel).tex(pixel, pixel * 12).endVertex();
		builder.pos( - pixel, pixel * height,  + pixel).tex(pixel * 3, pixel * 12).endVertex();
		builder.pos( - pixel, pixel * 2,  + pixel).tex(pixel * 3, pixel).endVertex();
		
		builder.pos( - pixel, pixel * height,  - pixel).tex(pixel*2, pixel*2).endVertex();
		builder.pos( - pixel, pixel * height,  + pixel).tex(pixel*2, pixel * 5).endVertex();
		builder.pos( + pixel, pixel * height,  + pixel).tex(pixel * 5, pixel * 5).endVertex();
		builder.pos( + pixel, pixel * height,  - pixel).tex(pixel * 5, pixel*2).endVertex();

		Tessellator.getInstance().draw();
		GlStateManager.popMatrix();
	}
	
	public static void bindTextureS(ResourceLocation rl){
		Minecraft.getMinecraft().renderEngine.bindTexture(rl);
	}
	
}
