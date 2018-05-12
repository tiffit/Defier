package net.tiffit.defier.item;

import java.util.List;

import cofh.redstoneflux.api.IEnergyContainerItem;
import cofh.redstoneflux.impl.EnergyStorage;
import cofh.redstoneflux.util.EnergyContainerItemWrapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class BatteryItem extends DefierItem implements IEnergyContainerItem {

	private int maxStore;

	public BatteryItem(String type, int maxStore) {
		super("battery." + type);
		this.maxStore = maxStore;
		setMaxStackSize(1);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new EnergyContainerItemWrapper(stack, this);
	}

	@Override
	public int receiveEnergy(ItemStack c, int maxReceive, boolean simulate) {
		EnergyStorage storage = getStorage(c);
		int value = storage.receiveEnergy(maxReceive, simulate);
		if (!simulate)
			setStorage(storage, c);
		return value;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		EnergyStorage storage = getStorage(stack);
		return 1 - storage.getEnergyStored() / (double) storage.getMaxEnergyStored();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		EnergyStorage storage = getStorage(stack);
		tooltip.add("Energy Stored: " + storage.getEnergyStored() + "RF/" + storage.getMaxEnergyStored() + "RF");
	}

	@Override
	public int extractEnergy(ItemStack c, int maxExtract, boolean simulate) {
		EnergyStorage storage = getStorage(c);
		int value = storage.extractEnergy(maxExtract, simulate);
		if (!simulate)
			setStorage(storage, c);
		return value;
	}

	@Override
	public int getEnergyStored(ItemStack c) {
		return getStorage(c).getEnergyStored();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		if (this.isInCreativeTab(tab)) {
			ItemStack full = new ItemStack(this);
			EnergyStorage storage = this.getStorage(full);
			storage.setEnergyStored(maxStore);
			setStorage(storage, full);
			items.add(full);
		}
	}

	protected EnergyStorage getStorage(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("energystorage")) {
			return new EnergyStorage(maxStore).readFromNBT(stack.getTagCompound().getCompoundTag("energystorage"));
		} else {
			EnergyStorage storage = new EnergyStorage(maxStore);
			setStorage(storage, stack);
			return storage;
		}
	}

	private void setStorage(EnergyStorage storage, ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound tag = new NBTTagCompound();
		storage.writeToNBT(tag);
		stack.getTagCompound().setTag("energystorage", tag);
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return maxStore;
	}

}
