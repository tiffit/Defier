package net.tiffit.defier.support.top;

import java.util.HashMap;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.tiffit.defier.Defier;
import net.tiffit.defier.tileentity.RFTileEntity;

public class TOPProvider implements IProbeConfigProvider, IProbeInfoProvider {

	public static HashMap<Class<? extends TileEntity>, TOPTileEntityInfo> TE_MAP = new HashMap<Class<? extends TileEntity>, TOPTileEntityInfo>();
	
	@Override
	public String getID() {
		return Defier.MODID + ".top";
	}
	
	@Override
	public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {}

	@Override
	public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		TileEntity te = world.getTileEntity(data.getPos());
		if(te == null)return;
		if (te instanceof RFTileEntity) {
			config.setRFMode(0);
		}
		TOPTileEntityInfo info = TE_MAP.get(te.getClass());
		if(info != null)info.getProbeConfig(te, config, player, world, blockState, data);
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		TileEntity te = world.getTileEntity(data.getPos());
		if(te == null)return;
		if (te instanceof RFTileEntity) {
			RFTileEntity rte = (RFTileEntity) te;
			probeInfo.progress(rte.rf.getEnergyStored(), rte.rf.getMaxEnergyStored(), probeInfo.defaultProgressStyle().suffix("RF").filledColor(Config.rfbarFilledColor).alternateFilledColor(Config.rfbarAlternateFilledColor).borderColor(Config.rfbarBorderColor).numberFormat(Config.rfFormat));
		}
		TOPTileEntityInfo info = TE_MAP.get(te.getClass());
		if(info != null)info.addProbeInfo(te, mode, probeInfo, player, world, blockState, data);
	}

}
