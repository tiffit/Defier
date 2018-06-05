package net.tiffit.defier.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.tiffit.defier.DefierItems;
import net.tiffit.defier.tileentity.DefierTileEntity;
import net.tiffit.tiffitlib.generics.GenericContainer;

public class DefierContainer extends GenericContainer {

	private DefierTileEntity te;

	public DefierContainer(IInventory playerInventory, TileEntity tilent) {
		super(playerInventory, tilent);
		this.te = (DefierTileEntity) tilent;

		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 22, 23){
			@Override
			public boolean isItemValid(ItemStack stack) {
				if(stack.getItem() != DefierItems.pattern || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("defieritem"))return false;
				return true;
			}
		});
		addSlotToContainer(new SlotItemHandler(itemHandler, 1, 131, 23){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
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

			if (index < 2) {
				if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
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
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !te.isInvalid() && playerIn.getDistanceSq(te.getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

}