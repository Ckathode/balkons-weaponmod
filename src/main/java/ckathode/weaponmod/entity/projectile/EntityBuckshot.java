package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.RangedComponent;

public class EntityBuckshot extends EntityProjectile
{
	public EntityBuckshot(World world)
	{
		super(world);
		setPickupMode(NO_PICKUP);
	}
	
	public EntityBuckshot(World world, double x, double y, double z)
	{
		this(world);
		setPosition(x, y, z);
	}
	
	public EntityBuckshot(World world, EntityLivingBase entityliving)
	{
		this(world);
		shootingEntity = entityliving;
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(motionX, motionY, motionZ, 5.0F, 10.0F);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (ticksInAir > 6)
		{
			setDead();
		}
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		float damage = 10F * (1F + extraDamage);
		
		DamageSource damagesource;
		if (shootingEntity == null)
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
		} else
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, shootingEntity);
		}
		
		int prevhurtrestime = entity.hurtResistantTime;
		if (entity.attackEntityFrom(damagesource, damage))
		{
			entity.hurtResistantTime = prevhurtrestime;
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
	public int getMaxArrowShake()
	{
		return 0;
	}
	
	@Override
	public float getGravity()
	{
		return getTotalVelocity() < 2F ? 0.04F : 0F;
	}
	
	public static void fireSpreadShot(World world, EntityLivingBase entityliving, RangedComponent item, ItemStack itemstack, int s)
	{
		EntityBuckshot entity;
		for (int i = 0; i < s; i++)
		{
			entity = new EntityBuckshot(world, entityliving);
			if (item != null && itemstack != null)
			{
				item.applyProjectileEnchantments(entity, itemstack);
			}
			world.spawnEntityInWorld(entity);
		}
	}
	
	public static void fireSpreadShot(World world, double x, double y, double z)
	{
		for (int i = 0; i < 6; i++)
		{
			world.spawnEntityInWorld(new EntityBuckshot(world, x, y, z));
		}
	}
	
	public static void fireFromDispenser(World world, double d, double d1, double d2, int i, int j, int k)
	{
		for (int i1 = 0; i1 < 6; i1++)
		{
			EntityBuckshot entityblundershot = new EntityBuckshot(world, d, d1, d2);
			
			entityblundershot.setThrowableHeading(i, j, k, 5.0F, 15.0F);
			world.spawnEntityInWorld(entityblundershot);
		}
	}
}
