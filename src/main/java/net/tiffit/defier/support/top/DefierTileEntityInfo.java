package net.tiffit.defier.support.top;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfig.ConfigMode;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.tiffit.defier.DefierRecipe;
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.defier.tileentity.DefierTileEntity;

public class DefierTileEntityInfo implements TOPTileEntityInfo{

	@Override
	public void getProbeConfig(TileEntity te, IProbeConfig config, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		config.showChestContents(ConfigMode.NOT);
	}

	@Override
	public void addProbeInfo(TileEntity te, ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		DefierTileEntity d = (DefierTileEntity) te;
		IItemHandler item = d.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		ItemStack pattern = item.getStackInSlot(0);
		if(pattern.isEmpty() || !pattern.hasTagCompound() || !pattern.getTagCompound().hasKey("defieritem")){
			probeInfo.text("No Pattern");
		}else{
			ItemStack stored = new ItemStack(pattern.getTagCompound().getCompoundTag("defieritem"));
			DefierRecipe recipe = DefierRecipeRegistry.findRecipeForStack(stored);
			if(recipe == null){
				probeInfo.text(TextFormatting.RED + "Pattern Error");
			}else{
				probeInfo.text(TextFormatting.AQUA + "Pattern: " + TextFormatting.RESET + IProbeInfo.STARTLOC + recipe.outputItem().getUnlocalizedName() + ".name" + IProbeInfo.ENDLOC);
				double progress = d.rf.getEnergyStored()/(double)d.rf.getMaxEnergyStored() * 100;
				probeInfo.text(TextFormatting.AQUA + "Progress: " + TextFormatting.RESET + ItemStack.DECIMALFORMAT.format(progress) + "%");
			}
		}
	}	

}
