package ckathode.weaponmod.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ckathode.weaponmod.AdvancedExplosion;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MsgExplosion extends WMMessage
{
	private double				x, y, z;
	private float				size;
	private List<BlockPos>	blocks;
	private boolean				smallParticles, bigParticles;

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

	public MsgExplosion()
	{
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buf)
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
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf)
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

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClientSide(EntityPlayer player)
	{
		World world = FMLClientHandler.instance().getWorldClient();
		AdvancedExplosion expl = new AdvancedExplosion(world, null, x, y, z, size, true, true);
		expl.setAffectedBlockPositions(blocks);
		expl.doParticleExplosion(smallParticles, bigParticles);
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}
}
