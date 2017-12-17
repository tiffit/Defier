package net.tiffit.defier.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.defier.ModItems;

public class PatternMolderTileEntity extends TileEntity{
	
    private ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
        	PatternMolderTileEntity te = PatternMolderTileEntity.this;
        	te.markDirty();
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        itemStackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
    }
    
    public void updateResult(){
    	ItemStack pattern = itemStackHandler.getStackInSlot(0);
    	ItemStack ingredient = itemStackHandler.getStackInSlot(1);
    	if(pattern.isEmpty() || ingredient.isEmpty() || DefierRecipeRegistry.findRecipeForStack(ingredient) == null){
    		itemStackHandler.setStackInSlot(2, ItemStack.EMPTY);
    		return;
    	}else{
    		ItemStack result = new ItemStack(ModItems.pattern);
    		if(!result.hasTagCompound())result.setTagCompound(new NBTTagCompound());
    		NBTTagCompound nbt = result.getTagCompound();
    		nbt.setTag("defieritem", ingredient.serializeNBT());
    		itemStackHandler.setStackInSlot(2, result);
    	}
    }
    
    @Override
    public void markDirty() {
    	updateResult();
    	super.markDirty();
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
        return super.getCapability(capability, facing);
    }
}
