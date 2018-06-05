package net.tiffit.defier.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.tiffit.defier.Defier;
import net.tiffit.defier.tileentity.CompressorTileEntity;
import net.tiffit.tiffitlib.generics.GenericContainer;
import net.tiffit.tiffitlib.generics.GenericGuiContainer;

public class CompressorGui extends GenericGuiContainer {

    private static final ResourceLocation background = new ResourceLocation(Defier.MODID, "textures/gui/compressor.png");

    private CompressorTileEntity te;
    
    public CompressorGui(TileEntity tilent, GenericContainer container) {
        super(tilent, container);
        this.te = (CompressorTileEntity) tilent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
    	this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    	int mx = mouseX - guiLeft;
    	int my = mouseY - guiTop;
    	drawCenteredStringWithoutShadow(fontRenderer, "Energy Usage", xSize/2, 6, 4210752);
    	drawCenteredStringWithoutShadow(fontRenderer, te.rfUsage+" RF/t", xSize/2, 16, 4210752);
    	if(mx >= 7 && my >= 75 && my <= 79 && mx <= 168){
    		drawHoveringText("Items Remaining: " + te.getProgress(), mx, my);
    	}
    	if(mx >= 7 && my >= 75 - 9 && my <= 79 - 9 && mx <= 168){
    		drawHoveringText("Energy: " + te.getEnergyStored(null), mx, my);
    	}
    }
    
    public void drawCenteredStringWithoutShadow(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawString(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        {
	    	double scale = 1 - (te.getProgress()/(double)te.max_progress);
	    	int bar = (int) (scale*160);
	    	if(bar > 0)drawTexturedModalRect(guiLeft + 8, guiTop + 76, 0, 166, bar, 2);
        }
        {
	    	double scale = te.getEnergyStored(null)/(double)te.getMaxEnergyStored(null);
	    	int bar = (int) (scale*160);
	    	if(bar > 0)drawTexturedModalRect(guiLeft + 8, guiTop + 76 - 9, 0, 166, bar, 2);
        }
    }
}