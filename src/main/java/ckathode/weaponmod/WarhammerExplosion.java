package ckathode.weaponmod;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WarhammerExplosion extends AdvancedExplosion
{
	public WarhammerExplosion(World world, Entity entity, double d, double d1, double d2, float f)
	{
		super(world, entity, d, d1, d2, f);
	}
	
	@Override
	public void doEntityExplosion(DamageSource damagesource)
	{
		float size = explosionSize * 2F;
		int i0 = MathHelper.floor_double(explosionX - size - 1.0D);
		int i1 = MathHelper.floor_double(explosionX + size + 1.0D);
		int j0 = MathHelper.floor_double(explosionY - size - 1.0D);
		int j1 = MathHelper.floor_double(explosionY + size + 1.0D);
		int k0 = MathHelper.floor_double(explosionZ - size - 1.0D);
		int k1 = MathHelper.floor_double(explosionZ + size + 1.0D);
		@SuppressWarnings("unchecked")
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, AxisAlignedBB.getBoundingBox(i0, j0, k0, i1, j1, k1));
		
		double dx;
		double dy;
		double dz;
		
		for (int i = 0; i < list.size(); i++)
		{
			Entity entity = list.get(i);
			double dr = entity.getDistance(explosionX, explosionY, explosionZ) / size;
			
			if (dr <= 1.0D)
			{
				dx = entity.posX - explosionX;
				dy = entity.posY - explosionY;
				dz = entity.posZ - explosionZ;
				double d = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
				dx /= d;
				dy /= d;
				dz /= d;
				double var36 = 1.0D - dr;
				int damage = (int) ((var36 * var36 + var36) / 2.0D * 8.0D * size + 1D);
				entity.attackEntityFrom(damagesource, damage);
				entity.motionX += dx * var36;
				entity.motionY += dy * var36;
				entity.motionZ += dz * var36;
			}
		}
	}
}
