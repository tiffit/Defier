package net.tiffit.defier.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.tiffit.defier.tileentity.RFTileEntity;

public class PacketUpdateRF implements IMessage {
	
    private BlockPos pos;
    private long rf;
    private long max;

    public PacketUpdateRF() {
    }
    
    public PacketUpdateRF(BlockPos pos, long rf, long max) {
    	this.pos = pos;
    	this.rf = rf;
    	this.max = max;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        rf = buf.readLong();
        max = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeLong(rf);
        buf.writeLong(max);
    }



    public static class Handler implements IMessageHandler<PacketUpdateRF, IMessage> {
        @Override
        public IMessage onMessage(PacketUpdateRF message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketUpdateRF message, MessageContext ctx) {
        	RFTileEntity te = (RFTileEntity) Minecraft.getMinecraft().world.getTileEntity(message.pos);
        	if(te != null){
        		te.getStorage().setCapacity(message.max);
        		te.getStorage().setEnergyStored(message.rf);
        	}
        }
    }
}