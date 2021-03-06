package net.tiffit.defier.tileentity;

import cofh.redstoneflux.api.IEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.tiffit.defier.network.PacketUpdateRF;
import net.tiffit.tiffitlib.network.NetworkManager;
import net.tiffit.tiffitlib.utils.LargeEnergyStorage;

public class RFTileEntity extends TileEntity implements IEnergyHandler {

	public LargeEnergyStorage rf;
	
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        rf.readFromNBT(compound);
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        rf.writeToNBT(compound);
        return compound;
    }
	
	@Override
	public int getEnergyStored(EnumFacing from) {
		if(rf.getEnergyStored() > Integer.MAX_VALUE)return Integer.MAX_VALUE;
		return (int)rf.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		if(rf.getMaxEnergyStored() > Integer.MAX_VALUE)return Integer.MAX_VALUE;
		return (int)rf.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return false;
	}
	
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }
    
    public LargeEnergyStorage getStorage(){
    	return rf;
    }
    
    public void syncRFClient(){
    	PacketUpdateRF packet = new PacketUpdateRF(getPos(), rf.getEnergyStored(), rf.getMaxEnergyStored());
		NetworkManager.NETWORK.sendToAllAround(packet, getSyncTargetPoint());
    }

    public TargetPoint getSyncTargetPoint(){
    	return NetworkManager.getSyncTargetPoint(this);
    }
    
}
