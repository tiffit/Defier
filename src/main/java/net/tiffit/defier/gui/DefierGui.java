package net.tiffit.defier.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.tiffit.defier.Defier;
import net.tiffit.defier.DefierRecipe;
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.defier.container.DefierContainer;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.tiffitlib.TiffitLib;
import net.tiffit.tiffitlib.generics.GenericContainer;
import net.tiffit.tiffitlib.generics.GenericGuiContainer;

public class DefierGui extends GenericGuiContainer {

    private static final ResourceLocation background = new ResourceLocation(Defier.MODID, "textures/gui/defier.png");

    private DefierTileEntity te;
    private DefierContainer container;
    
    public DefierGui(TileEntity tilent, GenericContainer container) {
        super(tilent, container);
        this.te = (DefierTileEntity) tilent;
        this.container = (DefierContainer) container;
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
    	Slot patternSlot = container.getSlot(0);
    	if(patternSlot.getHasStack()){
    		ItemStack stored = new ItemStack(patternSlot.getStack().getTagCompound().getCompoundTag("defieritem"));
    		stored.clearCustomName();
    		drawCenteredString(fontRenderer, stored.getDisplayName(), xSize/2, 50, 0x00b5c6);
    		DefierRecipe recipe = DefierRecipeRegistry.findRecipeForStack(stored);
    		drawCenteredString(fontRenderer, TiffitLib.LARGE_NUMBER.format(te.rf.getEnergyStored()) + "RF/" + TiffitLib.LARGE_NUMBER.format(recipe.getCost()) + "RF", xSize/2, 60, 0xffffff);
    		double percent = te.rf.getEnergyStored()/(double)recipe.getCost();
    		drawCenteredString(fontRenderer, ItemStack.DECIMALFORMAT.format(percent*100) + "%", xSize/2, 70, 0xffffff);
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
    	Slot patternSlot = container.getSlot(0);
    	if(patternSlot.getHasStack()){
    		ItemStack stored = new ItemStack(patternSlot.getStack().getTagCompound().getCompoundTag("defieritem"));
    		stored.clearCustomName();
    		DefierRecipe recipe = DefierRecipeRegistry.findRecipeForStack(stored);
    		double percent = te.rf.getEnergyStored()/(double)recipe.getCost();
    		int scaled = (int) (percent*74);
            drawTexturedModalRect(guiLeft + 47, guiTop + 23, 176, 0, scaled, 17);

    	}
    }
}