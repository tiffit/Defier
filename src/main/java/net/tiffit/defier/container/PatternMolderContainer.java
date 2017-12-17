package net.tiffit.defier.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.defier.ModItems;
import net.tiffit.defier.tileentity.PatternMolderTileEntity;

public class PatternMolderContainer extends Container {

	private PatternMolderTileEntity te;

	public PatternMolderContainer(IInventory playerInventory, PatternMolderTileEntity te) {
		this.te = te;

		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 63, 28){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == ModItems.pattern;
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

	private void addPlayerSlots(IInventory playerInventory) {
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				int x = 8 + col * 18;
				int y = row * 18 + 84;
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
			}
		}
		for (int row = 0; row < 9; ++row) {
			int x = 8 + row * 18;
			int y = 58 + 84;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 3) {
                if (!this.mergeItemStack(itemstack1, 3, this.inventorySlots.size(), true)) {
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
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !te.isInvalid() && playerIn.getDistanceSq(te.getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

}