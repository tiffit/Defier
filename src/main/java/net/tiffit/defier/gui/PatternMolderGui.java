package net.tiffit.defier.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.tiffit.defier.Defier;
import net.tiffit.defier.container.PatternMolderContainer;
import net.tiffit.defier.tileentity.PatternMolderTileEntity;

public class PatternMolderGui extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation(Defier.MODID, "textures/gui/patternmolder.png");

    private PatternMolderTileEntity te;
    
    public PatternMolderGui(PatternMolderTileEntity te, PatternMolderContainer container) {
        super(container);
        this.te = te;
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