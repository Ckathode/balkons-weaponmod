package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ckathode.weaponmod.WeaponDamageSource;

public class EntityPierceBullet extends EntityProjectile
{
	public EntityPierceBullet(World world)
	{
		super(world);
		setPickupMode(NO_PICKUP);
	}
	
	public EntityPierceBullet(World world, double d, double d1, double d2)
	{
		this(world);
		setPosition(d, d1, d2);
	}
	
	public EntityPierceBullet(World world, EntityLivingBase entityliving, float speed, float deviation)
	{
		this(world);
		shootingEntity = entityliving;
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(motionX, motionY, motionZ, speed, deviation);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (inGround)
		{
			if (rand.nextInt(4) == 0)
			{
				worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
			}
			motionX *= 0.6D;
			motionY *= 0.6D;
			motionZ *= 0.6D;
			return;
		}
		double speed = getTotalVelocity();
		double amount = 16D;
		if (speed > 2.0D)
		{
			for (int i1 = 1; i1 < amount; i1++)
			{
				worldObj.spawnParticle("explode", posX + (motionX * i1) / amount, posY + (motionY * i1) / amount, posZ + (motionZ * i1) / amount, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		float damage = (40F + mainDamage) * (1F + extraDamage);
		DamageSource damagesource = null;
		if (shootingEntity == null)
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
		} else
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, shootingEntity);
		}
		if (entity.attackEntityFrom(damagesource, damage))
		{
			applyEntityHitEffects(entity);
			playHitSound();
		}
	}
	
	@Override
	public void onGroundHit(MovingObjectPosition mop)
	{
		double speed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
		if(speed>5.0) return;
		xTile = mop.blockX;
		yTile = mop.blockY;
		zTile = mop.blockZ;
		inTile = worldObj.getBlock(xTile, yTile, zTile);
		inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
		motionX = mop.hitVec.xCoord - posX;
		motionY = mop.hitVec.yCoord - posY;
		motionZ = mop.hitVec.zCoord - posZ;
		float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		posX -= motionX / f1 * 0.05D;
		posY -= motionY / f1 * 0.05D;
		posZ -= motionZ / f1 * 0.05D;
		inGround = true;
		beenInGround = true;//碰到方块
		setIsCritical(false);
		arrowShake = getMaxArrowShake();
		playHitSound();
		
		if (inTile != null)
		{
			inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
		}
	}
	
	@Override
	public boolean aimRotation()
	{
		return false;
	}
	
	@Override
	public int getMaxLifetime()
	{
		return 200;
	}
	
	@Override
	public float getAirResistance()//空气阻力？
	{
		return 0.98F;
	}
	
	@Override
	public float getGravity()
	{
		return getTotalVelocity() < 3F ? 0.07F : 0F;
	}
	
	@Override
	public int getMaxArrowShake()
	{
		return 0;
	}
}
