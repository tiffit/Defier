package net.tiffit.defier.client.tesr;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.tiffit.defier.Defier;
import net.tiffit.defier.client.render.lightning.LightningRender;
import net.tiffit.defier.proxy.ClientProxy;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;

public class EnergyProviderTESR extends TileEntitySpecialRenderer<EnergyProviderTileEntity> {

	private static ResourceLocation iron_block = new ResourceLocation("textures/blocks/iron_block.png");
	private static ResourceLocation obsidian = new ResourceLocation("textures/blocks/obsidian.png");

	private static LightningRender lightning;

	@Override
	public void render(EnergyProviderTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GlStateManager.disableLighting();
		bindTexture(iron_block);
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

		if (te.laser_timer > 0) {
			BlockPos newPos = te.laser_target.subtract(te.getPos());
			lightning = new LightningRender(new Vec3d(x + .5, y + .9, z + .5), new Vec3d(newPos.getX(), newPos.getY() - .4, newPos.getZ()));
			lightning.color = 0xaa0000;
			lightning.bendsMin = 5;
			lightning.bendsMax = 6;
			lightning.maxDeviation = .5;
			lightning.calculate();
			lightning.render();
		}

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
