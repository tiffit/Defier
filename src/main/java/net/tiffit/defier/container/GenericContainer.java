package net.tiffit.defier.container;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.nbt.NBTTagCompound;
import net.tiffit.defier.client.network.NetworkManager;
import net.tiffit.defier.client.network.PacketUpdateContainer;

public abstract class GenericContainer extends Container {

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);
			EntityPlayerMP mp = (EntityPlayerMP) icontainerlistener;
			NetworkManager.NETWORK.sendTo(new PacketUpdateContainer(getNBT()), mp);
		}
	}
	
	public NBTTagCompound getNBT(){
		return new NBTTagCompound();
	}
	
	public void readNBT(NBTTagCompound tag){
		
	}
	
}
