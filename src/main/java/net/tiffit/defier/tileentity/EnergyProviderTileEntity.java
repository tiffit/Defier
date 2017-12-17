package net.tiffit.defier.tileentity;

import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.tiffit.defier.client.network.NetworkManager;
import net.tiffit.defier.client.network.PacketEnergyProviderSend;
import net.tiffit.defier.client.network.PacketUpdateRF;
import net.tiffit.defier.util.LargeEnergyStorage;

public class EnergyProviderTileEntity extends RFTileEntity implements IEnergyReceiver, ITickable{

	private int max_delay = 100;
	private int delay = max_delay;
	
	private int upgrades = 0;
	
	public int laser_timer = 0;
	public BlockPos laser_target = null;
	public EnergyProviderTileEntity() {
		rf = new LargeEnergyStorage(1_000_000, 1_000_000, 0);
	}
    
	@Override
	public void update() {
		if(!world.isRemote){
			if(upgrades > 0){
				calcDelay();
				if(delay > max_delay)delay = max_delay;
			}
			DefierTileEntity te = findDefier();
			if(te != null){
				if(delay <= 0){
					delay = max_delay;
					long max = te.getStorage().getMaxEnergyStored();
					long current = te.getStorage().getEnergyStored();
					if(max > 0 && current < max){
						long amount = max - current;
						if(amount > rf.getEnergyStored())amount = rf.getEnergyStored();
						rf.setEnergyStored(rf.getEnergyStored() - amount);
						te.getStorage().setEnergyStored(current + amount);
						LargeEnergyStorage defier = te.rf;
						if(amount > 0){
							NetworkManager.NETWORK.sendToDimension(new PacketUpdateRF(getPos(), rf.getEnergyStored(), rf.getMaxEnergyStored()), world.provider.getDimension());
							NetworkManager.NETWORK.sendToDimension(new PacketUpdateRF(te.getPos(), defier.getEnergyStored(), defier.getMaxEnergyStored()), world.provider.getDimension());
							NetworkManager.NETWORK.sendToDimension(new PacketEnergyProviderSend(getPos(), te.getPos()), world.provider.getDimension());
						}
					}
				}else{
					delay--;
				}
			}else delay = max_delay;
		}else{
			if(laser_timer > 0){
				laser_timer--;
	    		Minecraft.getMinecraft().world.playSound(getPos(), SoundEvents.BLOCK_NOTE_XYLOPHONE, SoundCategory.BLOCKS, 0.1f, 0.1f, false);
	    		if(laser_timer <= 0){
	    			Minecraft.getMinecraft().world.playSound(getPos(), SoundEvents.BLOCK_NOTE_XYLOPHONE, SoundCategory.BLOCKS, 0.5f, 1f, false);
	    		}
			}
		}
	}
	
	public int getUpgrades(){
		return upgrades;
	}
	
	public void addUpgrade(){
		if(this.upgrades < 8)this.upgrades++;
	}
	
	public void calcDelay(){
		max_delay = 100 - upgrades*10;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("upgrades", upgrades);
		compound.setInteger("delay", delay);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		upgrades = compound.getInteger("upgrades");
		delay = compound.getInteger("delay");
		super.readFromNBT(compound);
	}
	
	public DefierTileEntity findDefier(){
		for(int x = -10; x <= 10; x++){
			for(int y = -10; y <= 10; y++){
				for(int z = -10; z <= 10; z++){
					BlockPos pos = getPos().add(x, y, z);
					if(pos.getY() <= 0 || pos.getY() > 256)continue;
					TileEntity ent = world.getTileEntity(pos);
					if(ent != null && ent instanceof DefierTileEntity)return (DefierTileEntity) ent;
				}
			}
		}
		return null;
	}

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(new net.minecraftforge.energy.IEnergyStorage() {

				@Override
				public int receiveEnergy(int maxReceive, boolean simulate) {

					return EnergyProviderTileEntity.this.receiveEnergy(facing, maxReceive, simulate);
				}

				@Override
				public int extractEnergy(int maxExtract, boolean simulate) {
					return 0;
				}

				@Override
				public int getEnergyStored() {

					return EnergyProviderTileEntity.this.getEnergyStored(facing);
				}

				@Override
				public int getMaxEnergyStored() {

					return EnergyProviderTileEntity.this.getMaxEnergyStored(facing);
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
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		int recieve = (int) rf.receiveEnergy(maxReceive, simulate);
		if(!world.isRemote)NetworkManager.NETWORK.sendToDimension(new PacketUpdateRF(getPos(), rf.getEnergyStored(), rf.getMaxEnergyStored()), world.provider.getDimension());
		markDirty();
		return recieve;
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}
	
}
