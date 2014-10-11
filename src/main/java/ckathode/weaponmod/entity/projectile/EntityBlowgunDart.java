package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.DartType;

public class EntityBlowgunDart extends EntityProjectile
{
	public EntityBlowgunDart(World world)
	{
		super(world);
	}
	
	public EntityBlowgunDart(World world, double d, double d1, double d2)
	{
		this(world);
		setPickupMode(PICKUP_ALL);
		setPosition(d, d1, d2);
	}
	
	public EntityBlowgunDart(World world, EntityLivingBase entityliving, float f)
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
		setThrowableHeading(motionX, motionY, motionZ, f * 2.0F, 1.0F);
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}
	
	public void setDartEffectType(int i)
	{
		dataWatcher.updateObject(17, Byte.valueOf((byte) i));
	}
	
	public int getDartEffectType()
	{
		return ((Byte) dataWatcher.getWatchableObjectByte(17)).intValue();
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		DamageSource damagesource = null;
		if (shootingEntity == null)
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
		} else
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, shootingEntity);
		}
		if (entity.attackEntityFrom(damagesource, 1 + extraDamage))
		{
			if (entity instanceof EntityLivingBase)
			{
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(DartType.dartTypes[getDartEffectType()].potionEffect));
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
	public float getGravity()
	{
		return 0.05F;
	}
	
	@Override
	public void playHitSound()
	{
		worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.2F));
	}
	
	@Override
	public int getMaxArrowShake()
	{
		return 4;
	}
	
	@Override
	public ItemStack getPickupItem()
	{
		return new ItemStack(BalkonsWeaponMod.dart, 1, getDartEffectType());
	}
}
