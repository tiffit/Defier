package net.tiffit.defier.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.tiffit.defier.DefierItems;
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.defier.util.DefierItemStackHandler;

public class PatternMolderTileEntity extends TileEntity{
	
    private DefierItemStackHandler itemStackHandler = new DefierItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
        	PatternMolderTileEntity te = PatternMolderTileEntity.this;
        	te.markDirty();
        }
        
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        	if(slot == 0)if(stack.getItem() != DefierItems.pattern)return stack;
        	if(slot == 1)if(DefierRecipeRegistry.findRecipeForStack(stack) == null)return stack;
        	if(slot == 2)return stack;
        	return super.insertItem(slot, stack, simulate);
        }
        @Override
        public int getSlotLimit(int slot) {
        	if(slot == 0)return 1;
        	if(slot == 2)return 1;
        	return super.getSlotLimit(slot);
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
    	ItemStack output = itemStackHandler.getStackInSlot(2);
    	if(pattern.isEmpty() || ingredient.isEmpty() || DefierRecipeRegistry.findRecipeForStack(ingredient) == null){
    		if(!output.isEmpty()){
    			itemStackHandler.setStackInSlot(2, ItemStack.EMPTY);
    		}
    	}else{
    		
    		ItemStack result = new ItemStack(DefierItems.pattern);
    		if(!result.hasTagCompound())result.setTagCompound(new NBTTagCompound());
    		NBTTagCompound nbt = result.getTagCompound();
    		nbt.setTag("defieritem", ingredient.serializeNBT());
    		if(output.isEmpty() || !ItemStack.areItemStacksEqual(result, output)){
    			itemStackHandler.setStackInSlot(2, result);
    		}
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
