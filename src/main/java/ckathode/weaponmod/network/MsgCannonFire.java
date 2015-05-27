package ckathode.weaponmod.network;

import ckathode.weaponmod.entity.EntityCannon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCannonFire extends WMMessage {

    private int	cannonEntityID	= 0;

    public MsgCannonFire() { }

    public MsgCannonFire(EntityCannon entity) {
        cannonEntityID = entity.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        cannonEntityID = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarShort(buf, cannonEntityID);
    }

    public static class Handler implements IMessageHandler<MsgCannonFire, IMessage> {

        @Override
        public IMessage onMessage(MsgCannonFire message, MessageContext ctx) {
            System.out.println(String.format("Received %s from %s", message.cannonEntityID, ctx.getServerHandler().playerEntity.getDisplayName()));

            Entity entity =  ctx.getServerHandler().playerEntity.worldObj.getEntityByID(message.cannonEntityID);
            if (entity instanceof EntityCannon)
            {
                ((EntityCannon) entity).fireCannon();
            }

            return null;
        }
    }
}
