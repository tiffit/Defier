package net.tiffit.defier.support.top;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;

public class EnergyProviderTileEntityInfo implements TOPTileEntityInfo{

	@Override
	public void getProbeConfig(TileEntity te, IProbeConfig config, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {}

	@Override
	public void addProbeInfo(TileEntity te, ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		EnergyProviderTileEntity pe = (EnergyProviderTileEntity) te;
		probeInfo.text(TextFormatting.DARK_GREEN + "Speed: " + TextFormatting.RESET + pe.getSpeedUpgrades() + "/8");
		probeInfo.text(TextFormatting.DARK_RED + "Energy: " + TextFormatting.RESET + pe.getStorageUpgrades() + "/4");
	}	

}
