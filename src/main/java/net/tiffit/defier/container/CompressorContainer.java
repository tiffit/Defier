package net.tiffit.defier.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.tiffit.defier.ConfigData;
import net.tiffit.defier.tileentity.CompressorTileEntity;
import net.tiffit.tiffitlib.generics.GenericContainer;

public class CompressorContainer extends GenericContainer {

	private CompressorTileEntity te;
	private int itemProgress;
	private int rfUsage;

	public CompressorContainer(IInventory playerInventory, TileEntity tilent) {
		super(playerInventory, tilent);
		this.te = (CompressorTileEntity) tilent;

		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 80, 34){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return !ConfigData.isCompressorBlacklisted(stack);
			}
		});
		
		addPlayerSlots(playerInventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(!mergeItemStack(itemstack1, 0, 1, false)){
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}
	
	@Override
	public NBTTagCompound getNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("progress", te.getProgress());
		tag.setInteger("rfusage", te.rfUsage);
		tag.setInteger("maxprogress", te.max_progress);
		tag.setLong("rf", te.rf.getEnergyStored());
		return tag;
	}
	
	@Override
	public void readNBT(NBTTagCompound tag) {
		te.setProgress(tag.getInteger("progress"));
		te.rfUsage = tag.getInteger("rfusage");
		te.max_progress = tag.getInteger("maxprogress");
		te.rf.setEnergyStored(tag.getLong("rf"));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !te.isInvalid() && playerIn.getDistanceSq(te.getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

}