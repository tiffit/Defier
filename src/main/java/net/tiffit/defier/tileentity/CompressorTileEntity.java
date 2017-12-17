package net.tiffit.defier.tileentity;

import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tiffit.defier.ConfigData;
import net.tiffit.defier.ModItems;
import net.tiffit.defier.util.LargeEnergyStorage;

public class CompressorTileEntity extends RFTileEntity implements IEnergyReceiver, ITickable{
	
	//For client-side syncing
	public int max_progress = ConfigData.COMPRESSOR_ITEM_AMOUNT;
	public int progress = ConfigData.COMPRESSOR_ITEM_AMOUNT;
	public int rfUsage;
	public boolean finished = false;
	
	public CompressorTileEntity(){
		rf = new LargeEnergyStorage(3_000_000, 150_000, 0);
	}
	
    private ItemStackHandler itemStackHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
        	CompressorTileEntity te = CompressorTileEntity.this;
        	te.markDirty();
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("itemProgress"))progress = compound.getInteger("itemProgress");
    }
    
    public int getProgress(){
    	return progress;
    }
    
    public void setProgress(int progress){
    	this.progress = progress;
    }

    public void finishCompression(){
    	if(world.isRemote || finished)return;
    	world.destroyBlock(getPos(), false);
    	EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ModItems.defiercore, 1));
    	item.setVelocity(0, 0.2, 0);
    	world.spawnEntity(item);
    	finished = true;
    }
    
    private int getItemsIn(){
    	return ConfigData.COMPRESSOR_ITEM_AMOUNT - progress;
    }
    
	@Override
	public void update() {
		if(world.isRemote)return;
		if(getItemsIn() > 64*5){
			int rfNeeded = (int)((getItemsIn()/(double)ConfigData.COMPRESSOR_ITEM_AMOUNT)*ConfigData.COMPRESSOR_RF_USAGE);
			if(rf.getEnergyStored() <= rfNeeded){
				world.destroyBlock(getPos(), false);
				if(ConfigData.COMPRESSOR_EXPLODE)world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (int)((100_000*(rfNeeded/(double)ConfigData.COMPRESSOR_RF_USAGE))/10_000), true);
			}else{
				rf.setEnergyStored(rf.getEnergyStored() - rfNeeded);
			}
			rfUsage = rfNeeded;
		}else rfUsage = 0;
	}
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("itemProgress", progress);
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(new net.minecraftforge.energy.IEnergyStorage() {

				@Override
				public int receiveEnergy(int maxReceive, boolean simulate) {

					return CompressorTileEntity.this.receiveEnergy(facing, maxReceive, simulate);
				}

				@Override
				public int extractEnergy(int maxExtract, boolean simulate) {
					return 0;
				}

				@Override
				public int getEnergyStored() {

					return CompressorTileEntity.this.getEnergyStored(facing);
				}

				@Override
				public int getMaxEnergyStored() {

					return CompressorTileEntity.this.getMaxEnergyStored(facing);
				}

				@Override
				public boolean canExtract() {
					return false;
				}

				@Override
				public boolean canReceive() {

					return true;
				}
			});
		}
        return super.getCapability(capability, facing);
    }

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return (int)rf.receiveEnergy(maxReceive, simulate);
	}


	
}
