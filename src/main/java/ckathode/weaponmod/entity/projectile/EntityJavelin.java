package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;

public class EntityJavelin extends EntityProjectile
{
	public EntityJavelin(World world)
	{
		super(world);
	}
	
	public EntityJavelin(World world, double x, double y, double z)
	{
		this(world);
		setPickupMode(PICKUP_ALL);
		setPosition(x, y, z);
	}
	
	public EntityJavelin(World world, EntityLivingBase entityliving, float f)
	{
		this(world);
		shootingEntity = entityliving;
		setPickupModeFromEntity(entityliving);
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(motionX, motionY, motionZ, f * 1.1F, 3.0F);
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		double vel = getTotalVelocity();
		int damage = MathHelper.ceiling_double_int(vel * (3D + extraDamage));
		if (getIsCritical())
		{
			damage += rand.nextInt(damage / 2 + 2);
		}
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
		} else
		{
			bounceBack();
		}
	}
	
	@Override
	public void playHitSound()
	{
		worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.9F));
	}
	
	@Override
	public boolean canBeCritical()
	{
		return true;
	}
	
	@Override
	public int getMaxArrowShake()
	{
		return 10;
	}
	
	@Override
	public float getGravity()
	{
		return 0.03F;
	}
	
	@Override
	public ItemStack getPickupItem()
	{
		return new ItemStack(BalkonsWeaponMod.javelin, 1);
	}
}
