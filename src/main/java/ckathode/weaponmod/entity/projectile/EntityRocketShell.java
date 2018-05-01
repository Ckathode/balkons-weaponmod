package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;

public class EntityRocketShell extends EntityProjectile
{
	
	public EntityRocketShell(World world)
	{
		super(world);
		setPickupMode(NO_PICKUP);
	}
	
	public EntityRocketShell(World world, double d, double d1, double d2)
	{
		this(world);
		setPosition(d, d1, d2);
	}
	
	public EntityRocketShell(World world, EntityLivingBase entityliving, float speed, float deviation)
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
		
		double speed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
		double amount = 8D;
		if (speed > 1.0D)
		{
			for (int i1 = 1; i1 < amount; i1++)
			{
				worldObj.spawnParticle("smoke", posX + (motionX * i1) / amount, posY + (motionY * i1) / amount, posZ + (motionZ * i1) / amount, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public void createCrater(float f)
	{
		if (worldObj.isRemote || isInWater()) return;
		
		setDead();
		PhysHelper.createAdvancedExplosion(worldObj, this, posX, posY, posZ, f, BalkonsWeaponMod.instance.modConfig.rocketDoesBlockDamage, true);
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		float damage = (20F + mainDamage) * (1F + extraDamage);
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
			float f = damage/6F;
			createCrater(f);
		}
	}

	@Override
	public void onGroundHit(MovingObjectPosition mop)
	{
		xTile = mop.blockX;
		yTile = mop.blockY;
		zTile = mop.blockZ;
		inTile = worldObj.getBlock(xTile, yTile, zTile);
		inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
		motionX = (float) (mop.hitVec.xCoord - posX);
		motionY = (float) (mop.hitVec.yCoord - posY);
		motionZ = (float) (mop.hitVec.zCoord - posZ);
		float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		posX -= (motionX / f1) * 0.05D;
		posY -= (motionY / f1) * 0.05D;
		posZ -= (motionZ / f1) * 0.05D;
		inGround = true;
		
		if (inTile != null)
		{
			inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
		}
		float damage = (20F + mainDamage) * (1F + extraDamage);
		float f = damage/4F;
		createCrater(f);
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
		return ticksInAir > 20F ? 0.07F : 0F;
	}
	
	@Override
	public int getMaxArrowShake()
	{
		return 0;
	}
	
	@Override
	public float getShadowSize()
	{
		return 0.5F;
	}
}
