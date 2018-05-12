package net.tiffit.defier.client.tesr;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.tiffit.defier.Defier;
import net.tiffit.defier.block.EnergyProviderModifierBlock;
import net.tiffit.defier.client.render.lightning.LightningRender;
import net.tiffit.defier.proxy.ClientProxy;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;

public class EnergyProviderTESR extends TileEntitySpecialRenderer<EnergyProviderTileEntity> {

	private static ResourceLocation obsidian = new ResourceLocation("textures/blocks/obsidian.png");

	private static LightningRender lightning;

	@Override
	public void render(EnergyProviderTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GlStateManager.disableLighting();
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite sprite = null;
		IBlockState belowBlock = te.getWorld().getBlockState(te.getPos().down());
		if(belowBlock != null && belowBlock.getBlock() instanceof EnergyProviderModifierBlock){
			sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(belowBlock).getParticleTexture();
		}else{
			sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.IRON_BLOCK.getDefaultState()).getParticleTexture();
		}
		GL11.glColor4d(1, 1, 1, 1);
		BufferBuilder builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		float pixel = 1 / 16F;
		
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
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		bindTexture(obsidian);
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
		GL11.glTranslated(x + 0.5, y + pixel * 14, z + 0.5);
		GL11.glScalef(0.25f, 0.25f, 0.25f);
		double percent = te.rf.getEnergyStored() / (double) te.rf.getMaxEnergyStored();
		GL11.glColor4d(0.6 + (percent * 0.4), 0.6 - (percent * 0.6), 0.6 - (percent * 0.6), 1);
		ResourceLocation rL = new ResourceLocation(Defier.MODID + ":textures/blocks/blank.png");
		Minecraft.getMinecraft().getTextureManager().bindTexture(rL);
		GL11.glCallList(ClientProxy.defierSphereIdOutside);
		GL11.glCallList(ClientProxy.defierSphereIdInside);
		GL11.glColor4d(1, 1, 1, 1);
		GL11.glPopMatrix();

	}

}
