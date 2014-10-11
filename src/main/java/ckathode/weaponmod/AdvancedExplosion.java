package ckathode.weaponmod;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class AdvancedExplosion extends Explosion
{
	public World		worldObj;
	protected boolean	blocksCalculated;
	
	public AdvancedExplosion(World world, Entity entity, double d, double d1, double d2, float f)
	{
		super(world, entity, d, d1, d2, f);
		worldObj = world;
	}
	
	public void setAffectedBlockPositions(List<ChunkPosition> list)
	{
		affectedBlockPositions = list;
		blocksCalculated = true;
	}
	
	public void doEntityExplosion()
	{
		doEntityExplosion(DamageSource.setExplosionSource(this));
	}
	
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
		Vec3 vec31 = Vec3.createVectorHelper(explosionX, explosionY, explosionZ);
		
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
				
				if (d != 0D)
				{
					dx /= d;
					dy /= d;
					dz /= d;
					double dens = worldObj.getBlockDensity(vec31, entity.boundingBox);
					double var36 = (1.0D - dr) * dens;
					int damage = (int) ((var36 * var36 + var36) / 2.0D * 8.0D * size + 1D);
					entity.attackEntityFrom(damagesource, damage);
					entity.motionX += dx * var36;
					entity.motionY += dy * var36;
					entity.motionZ += dz * var36;
				}
			}
		}
	}
	
	public void doBlockExplosion()
	{
		if (!blocksCalculated)
		{
			calculateBlockExplosion();
		}
		for (int i = affectedBlockPositions.size() - 1; i >= 0; i--)
		{
			ChunkPosition chunkposition = (ChunkPosition) affectedBlockPositions.get(i);
			int x = chunkposition.chunkPosX;
			int y = chunkposition.chunkPosY;
			int z = chunkposition.chunkPosZ;
			Block block = worldObj.getBlock(x, y, z);
			if (block != null)
			{
				if (block.canDropFromExplosion(this))
				{
					block.dropBlockAsItemWithChance(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), 1F / explosionSize, 0);
				}
				
				worldObj.setBlock(x, y, z, Blocks.air, 0, 3);
				block.onBlockDestroyedByExplosion(worldObj, x, y, z, this);
			}
		}
	}
	
	public void doParticleExplosion(boolean smallparticles, boolean bigparticles)
	{
		worldObj.playSoundEffect(explosionX, explosionY, explosionZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		if (bigparticles)
		{
			worldObj.spawnParticle("hugeexplosion", explosionX, explosionY, explosionZ, 0.0D, 0.0D, 0.0D);
		}
		if (!smallparticles) return;
		
		if (!blocksCalculated)
		{
			calculateBlockExplosion();
		}
		
		for (int i = affectedBlockPositions.size() - 1; i >= 0; i--)
		{
			ChunkPosition chunkposition = (ChunkPosition) affectedBlockPositions.get(i);
			int j = chunkposition.chunkPosX;
			int k = chunkposition.chunkPosY;
			int l = chunkposition.chunkPosZ;
			//int i1 = worldObj.getBlockId(j, k, l);
			double px = j + worldObj.rand.nextFloat();
			double py = k + worldObj.rand.nextFloat();
			double pz = l + worldObj.rand.nextFloat();
			double dx = px - explosionX;
			double dy = py - explosionY;
			double dz = pz - explosionZ;
			double distance = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
			dx /= distance;
			dy /= distance;
			dz /= distance;
			double d7 = 0.5D / (distance / explosionSize + 0.1D);
			d7 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
			dx *= d7;
			dy *= d7;
			dz *= d7;
			worldObj.spawnParticle("explode", (px + explosionX * 1.0D) / 2D, (py + explosionY * 1.0D) / 2D, (pz + explosionZ * 1.0D) / 2D, dx, dy, dz);
			worldObj.spawnParticle("smoke", px, py, pz, dx, dy, dz);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void calculateBlockExplosion()
	{
		byte maxsize = 16;
		Set<ChunkPosition> set = new HashSet<ChunkPosition>();
		int i;
		int j;
		int k;
		double dx;
		double dy;
		double dz;
		
		for (i = 0; i < maxsize; i++)
		{
			for (j = 0; j < maxsize; j++)
			{
				for (k = 0; k < maxsize; k++)
				{
					if (i == 0 || i == maxsize - 1 || j == 0 || j == maxsize - 1 || k == 0 || k == maxsize - 1)
					{
						double rx = (i / (maxsize - 1.0F) * 2.0F - 1.0F);
						double ry = (j / (maxsize - 1.0F) * 2.0F - 1.0F);
						double rz = (k / (maxsize - 1.0F) * 2.0F - 1.0F);
						double rd = Math.sqrt(rx * rx + ry * ry + rz * rz);
						rx /= rd;
						ry /= rd;
						rz /= rd;
						float strength = explosionSize * (0.7F + worldObj.rand.nextFloat() * 0.6F);
						dx = explosionX;
						dy = explosionY;
						dz = explosionZ;
						
						for (float f = 0.3F; strength > 0.0F; strength -= f * 0.75F)
						{
							int x = MathHelper.floor_double(dx);
							int y = MathHelper.floor_double(dy);
							int z = MathHelper.floor_double(dz);
							Block block = worldObj.getBlock(x, y, z);
							
							if (block != null)
							{
								strength -= (block.getExplosionResistance(exploder, worldObj, x, y, z, explosionX, explosionY, explosionZ) + 0.3F) * f;
							}
							
							if (strength > 0.0F)
							{
								set.add(new ChunkPosition(x, y, z));
							}
							
							dx += rx * f;
							dy += ry * f;
							dz += rz * f;
						}
					}
				}
			}
		}
		
		affectedBlockPositions.addAll(set);
		blocksCalculated = true;
	}
	
	protected static final Random	rand	= new Random();
}
