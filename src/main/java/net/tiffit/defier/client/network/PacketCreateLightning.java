package net.tiffit.defier.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.tiffit.defier.client.render.WorldRenderTask;
import net.tiffit.defier.client.render.lightning.LightningRender;
import net.tiffit.defier.proxy.ClientProxy;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;

public class PacketCreateLightning implements IMessage {
	
    private Vec3d pos;
    private Vec3d dest;
    private int color;

    public PacketCreateLightning() {
    }
    
    public PacketCreateLightning(Vec3d pos, Vec3d dest) {
    	this(pos, dest, 0xaa0000);
    }
    
    public PacketCreateLightning(BlockPos pos, BlockPos dest) {
    	this(new Vec3d(pos), new Vec3d(dest));
    }
    
    public PacketCreateLightning(Vec3d pos, Vec3d dest, int color) {
    	this.pos = pos;
    	this.dest = dest;
    	this.color = color;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
    	pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    	dest = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    	color = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeDouble(pos.x);
    	buf.writeDouble(pos.y);
    	buf.writeDouble(pos.z);
    	
    	buf.writeDouble(dest.x);
    	buf.writeDouble(dest.y);
    	buf.writeDouble(dest.z);
    	
    	buf.writeInt(color);
    }



    public static class Handler implements IMessageHandler<PacketCreateLightning, IMessage> {
        @Override
        public IMessage onMessage(PacketCreateLightning message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketCreateLightning message, MessageContext ctx) {
        	WorldRenderTask task = new WorldRenderTask(new Runnable() {
				
				@Override
				public void run() {
					Minecraft mc = Minecraft.getMinecraft();
					Vec3d newPos = message.dest.subtract(message.pos);
					LightningRender lightning = new LightningRender(message.pos.subtract(mc.player.getPositionVector()), newPos);
					lightning.color = message.color;
					lightning.bendsMin = 5;
					lightning.bendsMax = 6;
					lightning.maxDeviation = .5;
					lightning.calculate();
					lightning.render();
					mc.world.playSound(new BlockPos(message.pos.x, message.pos.y, message.pos.z), SoundEvents.BLOCK_NOTE_XYLOPHONE, SoundCategory.BLOCKS, 0.5f, .75f, false);
				}
			}, 10);
        	ClientProxy.addWorldRenderTask(task);
        }
    }
}