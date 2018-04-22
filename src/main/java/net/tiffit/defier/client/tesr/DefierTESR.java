package net.tiffit.defier.client.tesr;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.tiffit.defier.proxy.ClientProxy;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.defier.util.RenderUtils;

public class DefierTESR extends TileEntitySpecialRenderer<DefierTileEntity> {

	@Override
	public void render(DefierTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		GL11.glPushMatrix();
		if(te.render_pulsate_increase){
			te.render_pulsate++;
			if(te.render_pulsate >= 100){
				te.render_pulsate_increase = false;
			}
		}else{
			te.render_pulsate--;
			if(te.render_pulsate <= 0)te.render_pulsate_increase = true;
		}
		GL11.glTranslated(x+0.5, y + 0.5, z+0.5);
		float scale = 0.7F + te.render_pulsate/1000F;
		GL11.glScalef(scale, scale, scale);
		if(te.rf.getMaxEnergyStored() == 0)GL11.glColor4d(0, 0, 0, 1);
		else GL11.glColor4d(te.rf.getEnergyStored()/(double)te.rf.getMaxEnergyStored() * 0.7, 0, 0, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(RenderUtils.blank);
		GL11.glCallList(ClientProxy.defierSphereIdOutside);
		GL11.glCallList(ClientProxy.defierSphereIdInside);
		GL11.glColor4d(1, 1, 1, 1);
		GL11.glPopMatrix();
		
	}

}
