package net.tiffit.defier.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.tiffit.defier.Defier;

public class RenderUtils {
	
	public static ResourceLocation blank = new ResourceLocation(Defier.MODID + ":textures/blocks/blank.png");
	public static final float pixel = 1 / 16F;
	
	public static void renderLine(Vec3d start, Vec3d end, Vec3d origin, int c){
		Minecraft mc = Minecraft.getMinecraft();
		GL11.glPushMatrix();
		GL11.glTranslated(origin.x, origin.y, origin.z);
		mc.getTextureManager().bindTexture(blank);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.color((c >> 16 & 0xff) / 255F, (c >> 8 & 0xff) / 255F, (c & 0xff) / 255F);
        double xyDistance = Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.z - start.z, 2));
        GlStateManager.rotate((float)Math.toDegrees(Math.atan2(end.x, end.z)) - 90, 0, 1, 0);
        GlStateManager.rotate((float)Math.toDegrees(Math.atan2(end.y, xyDistance)), 0, 0, 1);
        double distance = start.distanceTo(end);
		BufferBuilder builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		
		builder.pos(start.x, start.y, start.z).tex(0, 0).endVertex();
		builder.pos(start.x, start.y + pixel, start.z).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y + pixel, start.z).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y, start.z).tex(0, 0).endVertex();
		
		builder.pos(start.x, start.y, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y + pixel, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x, start.y + pixel, start.z + pixel).tex(0, 0).endVertex();
		
		builder.pos(start.x, start.y + pixel, start.z).tex(0, 0).endVertex();
		builder.pos(start.x, start.y + pixel, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y + pixel, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y + pixel, start.z).tex(0, 0).endVertex();
		
		builder.pos(start.x, start.y, start.z).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y, start.z).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x, start.y, start.z + pixel).tex(0, 0).endVertex();
		Tessellator.getInstance().draw();
		GL11.glPopMatrix();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
	}
	
}
