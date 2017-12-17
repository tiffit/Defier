package net.tiffit.defier.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tiffit.defier.DefierRecipe;
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.defier.util.LargeEnergyStorage;

public class DefierTileEntity extends RFTileEntity implements ITickable{
	
	public int render_pulsate = 0;
	public boolean render_pulsate_increase = true;
	
    private ItemStackHandler itemStackHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
        	DefierTileEntity.this.markDirty();
        }
    };

    public DefierTileEntity() {
    	rf = new LargeEnergyStorage(0L);
	}
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        itemStackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
    }
    
	@Override
	public void update() {
		if(itemStackHandler.getStackInSlot(0).hasTagCompound() && itemStackHandler.getStackInSlot(0).getTagCompound().hasKey("defieritem")){
			DefierRecipe recipe = DefierRecipeRegistry.findRecipeForStack(new ItemStack(itemStackHandler.getStackInSlot(0).getTagCompound().getCompoundTag("defieritem")));
			if(recipe != null && itemStackHandler.insertItem(1, recipe.outputItem(), true) == ItemStack.EMPTY){
				rf.setCapacity(recipe.getCost());
				if(rf.getEnergyStored() == recipe.getCost()){
					rf.setEnergyStored(rf.getEnergyStored() - recipe.getCost());
					ItemStack output = itemStackHandler.getStackInSlot(1);
					if(output.isEmpty()){
						itemStackHandler.setStackInSlot(1, recipe.outputItem());
					}else{
						itemStackHandler.insertItem(1, recipe.outputItem(), false);
					}
				}
			}else rf.setCapacity(0);
		}else{
			rf.setCapacity(0);
		}
	}
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inventory", itemStackHandler.serializeNBT());
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
					return 0;
				}

				@Override
				public int extractEnergy(int maxExtract, boolean simulate) {
					return 0;
				}

				@Override
				public int getEnergyStored() {
					return DefierTileEntity.this.getEnergyStored(facing);
				}

				@Override
				public int getMaxEnergyStored() {
					return DefierTileEntity.this.getMaxEnergyStored(facing);
				}

				@Override
				public boolean canExtract() {
					return false;
				}

				@Override
				public boolean canReceive() {
					return false;
				}
			});
		}
        return super.getCapability(capability, facing);
    }
}
