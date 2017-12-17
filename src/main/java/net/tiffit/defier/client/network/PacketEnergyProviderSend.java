package net.tiffit.defier.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;

public class PacketEnergyProviderSend implements IMessage {
	
    private BlockPos pos;
    private BlockPos dest;

    public PacketEnergyProviderSend() {
    }
    
    public PacketEnergyProviderSend(BlockPos pos, BlockPos dest) {
    	this.pos = pos;
    	this.dest = dest;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        dest = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeLong(dest.toLong());
    }



    public static class Handler implements IMessageHandler<PacketEnergyProviderSend, IMessage> {
        @Override
        public IMessage onMessage(PacketEnergyProviderSend message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketEnergyProviderSend message, MessageContext ctx) {
        	EnergyProviderTileEntity te = (EnergyProviderTileEntity) Minecraft.getMinecraft().world.getTileEntity(message.pos);
        	if(te != null){
        		te.laser_timer = 20;
        		te.laser_target = message.dest;
        		Minecraft.getMinecraft().world.playSound(message.pos, SoundEvents.BLOCK_NOTE_XYLOPHONE, SoundCategory.BLOCKS, 0.5f, 1f, false);
        	}
        }
    }
}