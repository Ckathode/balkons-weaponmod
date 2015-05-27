package ckathode.weaponmod.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ExtendedReachHelper
{
	private static Minecraft	mc	= FMLClientHandler.instance().getClient();
	
	/**
	 * This method will return the entitly or tile the mouse is hovering over up to the distance provided. It is more or less a copy/paste of the default
	 * minecraft version.
	 * 
	 * @return
	 */
	public static MovingObjectPosition getMouseOver(float frame, float dist)
	{
		MovingObjectPosition mop = null;
		if (mc.getRenderViewEntity() != null)
		{
			if (mc.theWorld != null)
			{
				double var2 = dist;
				mop = mc.getRenderViewEntity().rayTrace(var2, frame);
				double calcdist = var2;
				BlockPos pos = mc.getRenderViewEntity().getPosition();
				var2 = calcdist;
				if (mop != null)
				{
					calcdist = mop.hitVec.distanceTo(new Vec3(pos.getX(), pos.getY(), pos.getZ()));
				}
				
				Vec3 lookvec = mc.getRenderViewEntity().getLook(frame);
				BlockPos var8 = pos.add(new Vec3i(lookvec.xCoord * var2, lookvec.yCoord * var2, lookvec.zCoord * var2));
				Entity pointedEntity = null;
				float var9 = 1.0F;
				@SuppressWarnings("unchecked")
				List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(lookvec.xCoord * var2, lookvec.yCoord * var2, lookvec.zCoord * var2).expand(var9, var9, var9));
				double d = calcdist;
				
				for (Entity entity : list)
				{
					if (entity.canBeCollidedWith())
					{
						float bordersize = entity.getCollisionBorderSize();
						AxisAlignedBB aabb = entity.getBoundingBox().expand(bordersize, bordersize, bordersize);
						MovingObjectPosition mop0 = aabb.calculateIntercept(new Vec3(pos.getX(), pos.getY(), pos.getZ()), new Vec3(var8.getX(), var8.getY(), var8.getZ()));
						
						if (aabb.isVecInside(new Vec3(pos.getX(), pos.getY(), pos.getZ())))
						{
							if (0.0D < d || d == 0.0D)
							{
								pointedEntity = entity;
								d = 0.0D;
							}
						} else if (mop0 != null)
						{
							double d1 = new Vec3(pos.getX(), pos.getY(), pos.getZ()).distanceTo(mop0.hitVec);
							
							if (d1 < d || d == 0.0D)
							{
								pointedEntity = entity;
								d = d1;
							}
						}
					}
				}
				
				if (pointedEntity != null && (d < calcdist || mop == null))
				{
					mop = new MovingObjectPosition(pointedEntity);
				}
			}
		}
		return mop;
	}
	
}
