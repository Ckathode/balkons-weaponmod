package ckathode.weaponmod.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class WMMessage
{
	public abstract void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer);
	
	public abstract void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer);
	
	@SideOnly(Side.CLIENT)
	public abstract void handleClientSide(EntityPlayer player);
	
	public abstract void handleServerSide(EntityPlayer player);
}
