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
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.defier.tileentity.PatternMolderTileEntity;
import net.tiffit.tiffitlib.generics.GenericContainer;

public class PatternMolderContainer extends GenericContainer {

	private PatternMolderTileEntity te;

	public PatternMolderContainer(IInventory playerInventory, TileEntity tilent) {
		super(playerInventory, tilent);
		this.te = (PatternMolderTileEntity) tilent;

		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 63, 28){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == DefierItems.pattern;
			}
			@Override
			public int getSlotStackLimit() {
				return 1;
			}
		});
		addSlotToContainer(new SlotItemHandler(itemHandler, 1, 97, 28){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return DefierRecipeRegistry.findRecipeForStack(stack) != null;
			}
		});
		addSlotToContainer(new SlotItemHandler(itemHandler, 2, 80, 56){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
			@Override
			public int getSlotStackLimit() {
				return 1;
			}
			@Override
			public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
				getSlot(0).decrStackSize(1);
				return stack;
			}
			
			@Override
			public void onSlotChanged() {
				if(!getHasStack())getSlot(0).decrStackSize(1);
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

            if (index < 3) {
                if (this.mergeItemStack(itemstack1, 3, this.inventorySlots.size(), true)) {
                	if(index == 2)inventorySlots.get(0).putStack(ItemStack.EMPTY);
                	te.updateResult();
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 3, false)) {
            	te.updateResult();
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        te.updateResult();
        return itemstack;
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !te.isInvalid() && playerIn.getDistanceSq(te.getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

}