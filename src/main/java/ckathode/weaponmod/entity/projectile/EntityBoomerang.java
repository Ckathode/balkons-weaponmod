package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;

public class EntityBoomerang extends EntityMaterialProjectile
{
	public static final double	RETURN_STRENGTH		= 0.05D;
	public static final float	MIN_FLOAT_STRENGTH	= 0.4F;
	
	private float				soundTimer;
	public float				floatStrength;
	
	public EntityBoomerang(World world)
	{
		super(world);
	}
	
	public EntityBoomerang(World world, double x, double y, double z)
	{
		this(world);
		setPosition(x, y, z);
	}
	
	public EntityBoomerang(World world, EntityLivingBase entityliving, ItemStack itemstack, float f)
	{
		this(world);
		shootingEntity = entityliving;
		setPickupModeFromEntity(entityliving);
		setThrownItemStack(itemstack);
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(motionX, motionY, motionZ, f, 5.0F);
		soundTimer = 0;
		floatStrength = Math.min(1.5F, f);
		dataWatcher.updateObject(29, Integer.valueOf(Float.floatToRawIntBits(floatStrength)));
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(29, Integer.valueOf(Float.floatToRawIntBits(0F)));
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		floatStrength = Float.intBitsToFloat(dataWatcher.getWatchableObjectInt(29));
		
		if (inGround) return;
		
		floatStrength *= 0.994F;
		if (floatStrength < MIN_FLOAT_STRENGTH)
		{
			if (getIsCritical())
			{
				setIsCritical(false);
			}
			floatStrength = 0F;
		}
		float limitedStrength = Math.min(1F, floatStrength);
		
		if (!beenInGround)
		{
			rotationYaw += 20F * floatStrength;
		}
		
		if (!beenInGround && shootingEntity != null && floatStrength > 0F)
		{
			double dx;
			double dy;
			double dz;
			dx = posX - shootingEntity.posX;
			dy = posY - shootingEntity.posY - shootingEntity.getEyeHeight();
			dz = posZ - shootingEntity.posZ;
			
			double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
			dx /= d;
			dy /= d;
			dz /= d;
			
			motionX -= RETURN_STRENGTH * dx;
			motionY -= RETURN_STRENGTH * dy;
			motionZ -= RETURN_STRENGTH * dz;
			
			soundTimer += limitedStrength;
			if (soundTimer > 3F)
			{
				worldObj.playSoundAtEntity(this, "random.bow", 0.6F, 1.0F / (rand.nextFloat() * 0.2F + 2.2F - limitedStrength));
				soundTimer %= 3F;
			}
		}
		
		dataWatcher.updateObject(29, Integer.valueOf(Float.floatToRawIntBits(floatStrength)));
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		if (worldObj.isRemote || floatStrength < MIN_FLOAT_STRENGTH) return;
		
		if (entity == shootingEntity)
		{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack item = getPickupItem();
				if (item == null) return;
				
				if (player.capabilities.isCreativeMode || player.inventory.addItemStackToInventory(item))
				{
					worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					onItemPickup(player);
					setDead();
					return;
				}
			}
			return;
		}
		
		DamageSource damagesource = null;
		if (shootingEntity == null)
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
		} else
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, shootingEntity);
		}
		float damage = ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 2 + extraDamage;
		damage += getMeleeHitDamage(entity);
		if (getIsCritical())
		{
			damage += 2;
		}
		if (entity.attackEntityFrom(damagesource, damage))
		{
			applyEntityHitEffects(entity);
			playHitSound();
			if (thrownItem.getItemDamage() + 1 > thrownItem.getMaxDamage())
			{
				thrownItem.stackSize--;
				setDead();
			} else
			{
				if (shootingEntity instanceof EntityLivingBase)
				{
					thrownItem.damageItem(1, (EntityLivingBase) shootingEntity);
				} else
				{
					thrownItem.attemptDamageItem(1, rand);
				}
				setVelocity(0D, 0D, 0D);
			}
		} else
		{
			bounceBack();
		}
	}
	
	@Override
	public void onGroundHit(MovingObjectPosition mop)
	{
		xTile = mop.blockX;
		yTile = mop.blockY;
		zTile = mop.blockZ;
		inTile = worldObj.getBlock(xTile, yTile, zTile);
		motionX = (float) (mop.hitVec.xCoord - posX);
		motionY = (float) (mop.hitVec.yCoord - posY);
		motionZ = (float) (mop.hitVec.zCoord - posZ);
		float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		posX -= (motionX / f1) * 0.05D;
		posY -= (motionY / f1) * 0.05D;
		posZ -= (motionZ / f1) * 0.05D;
		
		motionX *= -rand.nextFloat() * 0.5F;
		motionZ *= -rand.nextFloat() * 0.5F;
		motionY = rand.nextFloat() * 0.1F;
		if (mop.sideHit == 1)
		{
			inGround = true;
		} else
		{
			inGround = false;
		}
		setIsCritical(false);
		beenInGround = true;
		floatStrength = 0F;
		
		if (inTile != null)
		{
			inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
		}
	}
	
	@Override
	public void playHitSound()
	{
		worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.9F));
	}
	
	@Override
	public boolean aimRotation()
	{
		return beenInGround || floatStrength < MIN_FLOAT_STRENGTH;
	}
	
	@Override
	public int getMaxLifetime()
	{
		return pickupMode == PICKUP_ALL || pickupMode == PICKUP_OWNER ? 0 : 1200;
	}
	
	@Override
	public boolean canBeCritical()
	{
		return true;
	}
	
	@Override
	public int getMaxArrowShake()
	{
		return 0;
	}
	
	@Override
	public float getGravity()
	{
		return beenInGround || floatStrength < MIN_FLOAT_STRENGTH ? 0.05F : 0F;
	}
	
	@Override
	public float getAirResistance()
	{
		return 0.98F;
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer entityplayer)
	{
		//While flying, the boomerang can only be picked up by the owner player.
		if (!beenInGround && ticksInAir > 5 && floatStrength >= MIN_FLOAT_STRENGTH)
		{
			if (entityplayer == shootingEntity)
			{
				ItemStack item = getPickupItem();
				if (item == null) return;
				
				if (entityplayer.capabilities.isCreativeMode || entityplayer.inventory.addItemStackToInventory(item))
				{
					worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					onItemPickup(entityplayer);
					setDead();
					return;
				}
			}
		}
		
		//So if not flying, handle picking up as normal.
		super.onCollideWithPlayer(entityplayer);
	}
}
