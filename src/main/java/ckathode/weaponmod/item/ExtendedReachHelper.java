package ckathode.weaponmod.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		if (mc.renderViewEntity != null)
		{
			if (mc.theWorld != null)
			{
				double var2 = dist;
				mop = mc.renderViewEntity.rayTrace(var2, frame);
				double calcdist = var2;
				Vec3 pos = mc.renderViewEntity.getPosition(frame);
				var2 = calcdist;
				if (mop != null)
				{
					calcdist = mop.hitVec.distanceTo(pos);
				}
				
				Vec3 lookvec = mc.renderViewEntity.getLook(frame);
				Vec3 var8 = pos.addVector(lookvec.xCoord * var2, lookvec.yCoord * var2, lookvec.zCoord * var2);
				Entity pointedEntity = null;
				float var9 = 1.0F;
				@SuppressWarnings("unchecked")
				List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(lookvec.xCoord * var2, lookvec.yCoord * var2, lookvec.zCoord * var2).expand(var9, var9, var9));
				double d = calcdist;
				
				for (Entity entity : list)
				{
					if (entity.canBeCollidedWith())
					{
						float bordersize = entity.getCollisionBorderSize();
						AxisAlignedBB aabb = entity.boundingBox.expand(bordersize, bordersize, bordersize);
						MovingObjectPosition mop0 = aabb.calculateIntercept(pos, var8);
						
						if (aabb.isVecInside(pos))
						{
							if (0.0D < d || d == 0.0D)
							{
								pointedEntity = entity;
								d = 0.0D;
							}
						} else if (mop0 != null)
						{
							double d1 = pos.distanceTo(mop0.hitVec);
							
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
