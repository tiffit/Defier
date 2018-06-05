package net.tiffit.defier.tileentity;

import java.util.List;

import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.oredict.OreDictionary;
import net.tiffit.defier.ConfigData;
import net.tiffit.defier.block.EnergyProviderModifierBlock;
import net.tiffit.defier.block.EnergyProviderModifierBlock.ModifierType;
import net.tiffit.tiffitlib.network.NetworkManager;
import net.tiffit.tiffitlib.network.PacketCreateLightning;
import net.tiffit.tiffitlib.utils.LargeEnergyStorage;

public class EnergyProviderTileEntity extends RFTileEntity implements IEnergyReceiver, ITickable {

	private int max_delay = 100;
	private int delay = max_delay;

	private int speed_upgrades = 0;
	private int storage_upgrades = 0;
	
	private ProviderColor color;

	public EnergyProviderTileEntity() {
		rf = new LargeEnergyStorage(ConfigData.ENERGYPROVIDER_MAX_STORAGE, ConfigData.ENERGYPROVIDER_MAX_STORAGE, 0);
		color = ProviderColor.Red;
	}
	
	public static enum ProviderColor{
		Black(0), Red(0xaa0000), Green(0x00aa00),
		Brown(0xf4aa42), Blue(0x0000aa), Purple(0x941bc4),
		Cyan(0x2288d6), Light_Gray(0xb7b7b7), Gray(0x6d6d6d),
		Pink(0xff429d), Lime(0x8dff41), Yellow(0xfffa0c),
		Light_Blue(0x3f4fff), Magenta(0xe500ff), Orange(0xffbb00),
		White(0xffffff);
		
		public final int color;
		
		ProviderColor(int color){
			this.color = color;
		}
		
		public String getDyeName(){
			return "dye" + name().replaceAll("_", "");
		}
		
		public static ProviderColor getDye(ItemStack stack){
			int[] ids = OreDictionary.getOreIDs(stack);
			for(int id : ids){
				for(ProviderColor color : ProviderColor.values()){
					if(color.getDyeName().equals(OreDictionary.getOreName(id)))return color;
				}
			}
			return null;
		}
	}

	@Override
	public void update() {
		ModifierType mod = getMod();
		if (!world.isRemote) {
			if (speed_upgrades > 0 || storage_upgrades > 0) {
				calcUpgrades();
				if (delay > max_delay)
					delay = max_delay;
			}
			if (mod == ModifierType.Attack) {
				if (delay <= 0) {
					if (rf.getEnergyStored() >= ConfigData.MODIFIER_ATTACK_COST) {
						delay = max_delay;
						int range = ConfigData.MODIFIER_ATTACK_RANGE;
						List<EntityMob> found = (List<EntityMob>) world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(pos.down(range).south(range).east(range), pos.up(range).north(range).west(range)));
						if (found.size() > 0) {
							EntityMob mob = found.get(0);
							mob.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 5 * (storage_upgrades + 1));
							NetworkManager.NETWORK.sendToAllAround(new PacketCreateLightning(new Vec3d(getPos()).addVector(.5, .9, .5), mob.getPositionVector().addVector(0, mob.height / 2, 0), color.color), getSyncTargetPoint());
							rf.setEnergyStored(rf.getEnergyStored()-ConfigData.MODIFIER_ATTACK_COST);
							syncRFClient();
						}
					}
				} else {
					delay--;
				}
			} else {
				DefierTileEntity te = findDefier();
				if (te != null) {
					if (delay <= 0) {
						delay = max_delay;
						long max = te.getStorage().getMaxEnergyStored();
						long current = te.getStorage().getEnergyStored();
						if (max > 0 && current < max) {
							long amount = max - current;
							if (amount > rf.getEnergyStored())
								amount = rf.getEnergyStored();
							rf.setEnergyStored(rf.getEnergyStored() - amount);
							te.getStorage().setEnergyStored(current + amount + (mod == ModifierType.Efficiency ? (int)(amount*0.05) : 0));
							if (amount > 0) {
								syncRFClient();
								te.syncRFClient();
								NetworkManager.NETWORK.sendToAllAround(new PacketCreateLightning(new Vec3d(getPos()).addVector(.5, .9, .5), new Vec3d(te.getPos()).addVector(.5, .5, .5), color.color), getSyncTargetPoint());
							}
						}
					} else {
						delay--;
					}
				} else
					delay = max_delay;
			}
		}
	}

	public ModifierType getMod() {
		IBlockState state = world.getBlockState(getPos().down());
		if (state != null && state.getBlock() instanceof EnergyProviderModifierBlock) {
			return ((EnergyProviderModifierBlock) state.getBlock()).modifier;
		}
		return null;
	}

	public int getSpeedUpgrades() {
		return speed_upgrades;
	}

	public int getStorageUpgrades() {
		return storage_upgrades;
	}

	public void addSpeedUpgrade() {
		if (this.speed_upgrades < 8)
			this.speed_upgrades++;
	}

	public void addStorageUpgrade() {
		if (this.storage_upgrades < 4)
			this.storage_upgrades++;
	}
	
	public void setColor(ProviderColor color){
		this.color = color;
	}

	public void calcUpgrades() {
		max_delay = 100 - speed_upgrades * 10;
		rf.setCapacity((long) (ConfigData.ENERGYPROVIDER_MAX_STORAGE * (Math.pow(ConfigData.ENERGYPROVIDER_STORAGE_BASE, storage_upgrades))));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("upgrades", speed_upgrades);
		compound.setInteger("storage_upgrades", storage_upgrades);
		compound.setInteger("delay", delay);
		compound.setInteger("color", color.ordinal());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		speed_upgrades = compound.getInteger("upgrades");
		storage_upgrades = compound.getInteger("storage_upgrades");
		delay = compound.getInteger("delay");
		if(compound.hasKey("color"))color = ProviderColor.values()[compound.getInteger("color")];
		calcUpgrades();
		super.readFromNBT(compound);
	}

	public DefierTileEntity findDefier() {
		DefierTileEntity nearestTE = null;
		int searchRange = 10;
		if(getMod() == ModifierType.Range)searchRange += 10;
		double nearestDistance = searchRange*searchRange + 1;
		for (int x = -searchRange; x <= searchRange; x++) {
			for (int y = -searchRange; y <= searchRange; y++) {
				for (int z = -searchRange; z <= searchRange; z++) {
					BlockPos pos = getPos().add(x, y, z);
					if (pos.getY() <= 0 || pos.getY() > 256)
						continue;
					TileEntity ent = world.getTileEntity(pos);
					double distance = pos.distanceSq(getPos());
					if (ent != null && ent instanceof DefierTileEntity && distance < nearestDistance){
						nearestTE = (DefierTileEntity) ent;
						nearestDistance = distance;
					}
				}
			}
		}
		return nearestTE;
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
		if (!world.isRemote)syncRFClient();
		markDirty();
		return recieve;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

}
