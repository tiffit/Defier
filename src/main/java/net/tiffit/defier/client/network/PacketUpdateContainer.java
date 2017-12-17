package net.tiffit.defier.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.tiffit.defier.container.GenericContainer;

public class PacketUpdateContainer implements IMessage {
	
    private NBTTagCompound tag;

    public PacketUpdateContainer() {
    }
    
    public PacketUpdateContainer(NBTTagCompound tag) {
    	this.tag = tag;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	ByteBufUtils.writeTag(buf, tag);
    }



    public static class Handler implements IMessageHandler<PacketUpdateContainer, IMessage> {
        @Override
        public IMessage onMessage(PacketUpdateContainer message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketUpdateContainer message, MessageContext ctx) {
        	GenericContainer container = (GenericContainer) Minecraft.getMinecraft().player.openContainer;
        	container.readNBT(message.tag);
        }
    }
}