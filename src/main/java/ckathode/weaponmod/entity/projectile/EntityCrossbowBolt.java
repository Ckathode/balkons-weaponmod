package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;

public class EntityCrossbowBolt extends EntityProjectile
{
	public EntityCrossbowBolt(World world)
	{
		super(world);
	}
	
	public EntityCrossbowBolt(World world, double d, double d1, double d2)
	{
		this(world);
		setPickupMode(PICKUP_ALL);
		setPosition(d, d1, d2);
	}
	
	public EntityCrossbowBolt(World world, EntityLivingBase entityliving, float deviation)
	{
		this(world);
		shootingEntity = entityliving;
		setPickupModeFromEntity(entityliving);
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(motionX, motionY, motionZ, 5.0F, deviation);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		float vel = (float) getTotalVelocity();
		float damage = vel * 4 + extraDamage;
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
			if (entity instanceof EntityLivingBase && worldObj.isRemote)
			{
				((EntityLivingBase) entity).setArrowCountInEntity(((EntityLivingBase) entity).getArrowCountInEntity() + 1);
			}
			applyEntityHitEffects(entity);
			playHitSound();
			setDead();
		} else
		{
			bounceBack();
		}
	}
	
	@Override
	public void playHitSound()
	{
		worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.4F));
	}
	
	@Override
	public int getMaxArrowShake()
	{
		return 4;
	}
	
	@Override
	public ItemStack getPickupItem()
	{
		return new ItemStack(BalkonsWeaponMod.bolt, 1);
	}
}
