package net.tiffit.defier.util;

import net.minecraft.nbt.NBTTagCompound;

public class LargeEnergyStorage implements ILargeEnergyStorage {

	protected long energy;
	protected long capacity;
	protected long maxReceive;
	protected long maxExtract;

	public LargeEnergyStorage(long capacity) {

		this(capacity, capacity, capacity);
	}

	public LargeEnergyStorage(long capacity, long maxTransfer) {

		this(capacity, maxTransfer, maxTransfer);
	}

	public LargeEnergyStorage(long capacity, long maxReceive, long maxExtract) {

		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	public LargeEnergyStorage readFromNBT(NBTTagCompound nbt) {

		this.energy = nbt.getLong("Energy");

		if (energy > capacity) {
			energy = capacity;
		}
		return this;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		if (energy < 0) {
			energy = 0;
		}
		nbt.setLong("Energy", energy);
		return nbt;
	}

	public LargeEnergyStorage setCapacity(long capacity) {

		this.capacity = capacity;

		if (energy > capacity) {
			energy = capacity;
		}
		return this;
	}

	public LargeEnergyStorage setMaxTransfer(long maxTransfer) {

		setMaxReceive(maxTransfer);
		setMaxExtract(maxTransfer);
		return this;
	}

	public LargeEnergyStorage setMaxReceive(long maxReceive) {

		this.maxReceive = maxReceive;
		return this;
	}

	public LargeEnergyStorage setMaxExtract(long maxExtract) {

		this.maxExtract = maxExtract;
		return this;
	}

	public long getMaxReceive() {

		return maxReceive;
	}

	public long getMaxExtract() {

		return maxExtract;
	}

	/**
	 * This function is included to allow for server to client sync. Do not call this externally to the containing Tile Entity, as not all IEnergyHandlers are guaranteed to have it.
	 */
	public void setEnergyStored(long energy) {
		this.energy = energy;
		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	/**
	 * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the EnergyStorage. Do not rely on this externally, as not all IEnergyHandlers are guaranteed to have it.
	 */
	public void modifyEnergyStored(long energy) {

		this.energy += energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	/* IEnergyStorage */
	@Override
	public long receiveEnergy(long maxReceive, boolean simulate) {

		long energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public long extractEnergy(long maxExtract, boolean simulate) {

		long energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public long getEnergyStored() {

		return energy;
	}

	@Override
	public long getMaxEnergyStored() {

		return capacity;
	}
}