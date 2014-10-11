package ckathode.weaponmod.entity.projectile;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;

public class EntityKnife extends EntityMaterialProjectile
{
	private int	soundTimer;
	
	public EntityKnife(World world)
	{
		super(world);
	}
	
	public EntityKnife(World world, double d, double d1, double d2)
	{
		this(world);
		setPosition(d, d1, d2);
	}
	
	public EntityKnife(World world, EntityLivingBase entityliving, ItemStack itemstack)
	{
		this(world);
		shootingEntity = entityliving;
		setPickupModeFromEntity(entityliving);
		setThrownItemStack(itemstack);
		soundTimer = 0;
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(motionX, motionY, motionZ, 0.8F, 3.0F);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (inGround || beenInGround) return;
		
		rotationPitch -= 70F;
		
		if (soundTimer >= 3)
		{
			if (!isInsideOfMaterial(Material.water))
			{
				worldObj.playSoundAtEntity(this, "random.bow", 0.6F, 1.0F / (rand.nextFloat() * 0.2F + 0.6F + ticksInAir / 15F));
			}
			soundTimer = 0;
		}
		soundTimer++;
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		if (worldObj.isRemote) return;
		DamageSource damagesource = null;
		if (shootingEntity == null)
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
		} else
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, shootingEntity);
		}
		if (entity.attackEntityFrom(damagesource, ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().getEntityDamage() + getMeleeHitDamage(entity)))
		{
			applyEntityHitEffects(entity);
			if (thrownItem.getItemDamage() + 2 > thrownItem.getMaxDamage())
			{
				thrownItem.stackSize--;
				setDead();
			} else
			{
				if (shootingEntity instanceof EntityLivingBase)
				{
					thrownItem.damageItem(2, (EntityLivingBase) shootingEntity);
				} else
				{
					thrownItem.attemptDamageItem(2, rand);
				}
				setVelocity(0D, 0D, 0D);
			}
		} else
		{
			bounceBack();
		}
	}
	
	@Override
	public boolean aimRotation()
	{
		return beenInGround;
	}
	
	@Override
	public int getMaxLifetime()
	{
		return pickupMode == PICKUP_ALL || pickupMode == PICKUP_OWNER ? 0 : 1200;
	}
	
	@Override
	public int getMaxArrowShake()
	{
		return 4;
	}
	
	@Override
	public float getGravity()
	{
		return 0.03F;
	}
	
	@Override
	public float getAirResistance()
	{
		return 0.98F;
	}
}