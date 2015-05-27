package ckathode.weaponmod.network;

import ckathode.weaponmod.entity.EntityCannon;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ckathode.weaponmod.AdvancedExplosion;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MsgExplosion extends WMMessage {
	private double				x, y, z;
	private float				size;
	private List<BlockPos>	blocks;
	private boolean				smallParticles, bigParticles;

	public MsgExplosion() { }

	@SuppressWarnings("unchecked")
	public MsgExplosion(AdvancedExplosion explosion, boolean smallparts, boolean bigparts)
	{
		x = explosion.getPosition().xCoord;
		y = explosion.getPosition().yCoord;
		z = explosion.getPosition().zCoord;
		size = explosion.explosionSize;
		blocks = explosion.affectedBlockPositions;
		smallParticles = smallparts;
		bigParticles = bigparts;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		size = buf.readFloat();
		smallParticles = buf.readBoolean();
		bigParticles = buf.readBoolean();

		int size = buf.readInt();
		blocks = new ArrayList<BlockPos>(size);
		for (int i = 0; i < size; i++)
		{
			int ix = buf.readByte() + (int) x;
			int iy = buf.readByte() + (int) y;
			int iz = buf.readByte() + (int) z;
			blocks.add(new BlockPos(ix, iy, iz));
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(size);
		buf.writeBoolean(smallParticles);
		buf.writeBoolean(bigParticles);

		int n = blocks.size();
		buf.writeInt(n);
		for (int i = 0; i < n; i++)
		{
			BlockPos pos = blocks.get(i);
			int dx = (int) pos.getX() - (int) x;
			int dy = (int) pos.getY() - (int) y;
			int dz = (int) pos.getZ() - (int) z;
			buf.writeByte(dx);
			buf.writeByte(dy);
			buf.writeByte(dz);
		}
	}

	public static class Handler implements IMessageHandler<MsgExplosion, IMessage> {

		@Override
		public IMessage onMessage(MsgExplosion message, MessageContext ctx) {
			World world = FMLClientHandler.instance().getWorldClient();
			AdvancedExplosion expl = new AdvancedExplosion(world, null, message.x, message.y, message.z, message.size, true, true);
			expl.setAffectedBlockPositions(message.blocks);
			expl.doParticleExplosion(message.smallParticles, message.bigParticles);

			return null;
		}
	}
}
