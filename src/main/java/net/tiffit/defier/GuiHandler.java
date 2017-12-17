package net.tiffit.defier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.tiffit.defier.container.CompressorContainer;
import net.tiffit.defier.container.DefierContainer;
import net.tiffit.defier.container.PatternMolderContainer;
import net.tiffit.defier.gui.CompressorGui;
import net.tiffit.defier.gui.DefierGui;
import net.tiffit.defier.gui.PatternMolderGui;
import net.tiffit.defier.tileentity.CompressorTileEntity;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.defier.tileentity.PatternMolderTileEntity;

public class GuiHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof CompressorTileEntity) {
            return new CompressorContainer(player.inventory, (CompressorTileEntity) te);
        }
        if (te instanceof DefierTileEntity) {
            return new DefierContainer(player.inventory, (DefierTileEntity) te);
        }
        if (te instanceof PatternMolderTileEntity) {
            return new PatternMolderContainer(player.inventory, (PatternMolderTileEntity) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof CompressorTileEntity) {
        	CompressorTileEntity compressor = (CompressorTileEntity) te;
            return new CompressorGui(compressor, new CompressorContainer(player.inventory, compressor));
        }
        if (te instanceof DefierTileEntity) {
        	DefierTileEntity defier = (DefierTileEntity) te;
            return new DefierGui(defier, new DefierContainer(player.inventory, defier));
        }
        if (te instanceof PatternMolderTileEntity) {
        	PatternMolderTileEntity patternmolder = (PatternMolderTileEntity) te;
            return new PatternMolderGui(patternmolder, new PatternMolderContainer(player.inventory, patternmolder));
        }
        return null;
    }
	
}
