package ckathode.weaponmod.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import ckathode.weaponmod.entity.EntityCannon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MsgCannonFire extends WMMessage
{
	private int	cannonEntityID	= 0;
	
	public MsgCannonFire()
	{
	}
	
	public MsgCannonFire(EntityCannon entity)
	{
		cannonEntityID = entity.getEntityId();
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buf)
	{
		cannonEntityID = buf.readInt();
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf)
	{
		buf.writeInt(cannonEntityID);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void handleClientSide(EntityPlayer player)
	{
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		Entity entity = player.worldObj.getEntityByID(cannonEntityID);
		if (entity instanceof EntityCannon)
		{
			((EntityCannon) entity).fireCannon();
		}
	}
	
}
