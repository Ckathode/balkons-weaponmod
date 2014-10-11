package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.WeaponDamageSource;

public class EntityMusketBullet extends EntityProjectile
{
	
	public EntityMusketBullet(World world)
	{
		super(world);
		setPickupMode(NO_PICKUP);
	}
	
	public EntityMusketBullet(World world, double d, double d1, double d2)
	{
		this(world);
		setPosition(d, d1, d2);
	}
	
	public EntityMusketBullet(World world, EntityLivingBase entityliving, float deviation)
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
		setThrowableHeading(motionX, motionY, motionZ, 5.0F, deviation);
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
		float damage = 20F + extraDamage;
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
			setDead();
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
	public float getAirResistance()
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
