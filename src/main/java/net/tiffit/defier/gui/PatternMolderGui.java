package net.tiffit.defier.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.tiffit.defier.Defier;
import net.tiffit.tiffitlib.generics.GenericContainer;
import net.tiffit.tiffitlib.generics.GenericGuiContainer;

public class PatternMolderGui extends GenericGuiContainer {

    private static final ResourceLocation background = new ResourceLocation(Defier.MODID, "textures/gui/patternmolder.png");
    
    public PatternMolderGui(TileEntity tilent, GenericContainer container) {
        super(tilent, container);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
    	this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    public void drawCenteredStringWithoutShadow(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawString(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}