package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.entity.EntityCannon;

public class EntityCannonBall extends EntityProjectile
{
	public EntityCannonBall(World world)
	{
		super(world);
	}
	
	public EntityCannonBall(World world, double d, double d1, double d2)
	{
		this(world);
		setPosition(d, d1, d2);
		yOffset = 0.0F;
	}
	
	public EntityCannonBall(World world, EntityCannon entitycannon, boolean superPowered)
	{
		this(world);
		shootingEntity = entitycannon.riddenByEntity;
		if (entitycannon.riddenByEntity instanceof EntityLivingBase)
		{
			setPickupModeFromEntity((EntityLivingBase) entitycannon.riddenByEntity);
		} else
		{
			setPickupMode(PICKUP_ALL);
		}
		setSize(0.5F, 0.5F);
		setLocationAndAngles(entitycannon.posX, entitycannon.posY + 1.0D, entitycannon.posZ, entitycannon.riddenByEntity.rotationYaw, entitycannon.riddenByEntity.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		yOffset = 0.0F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		posX += motionX * 1.2F;
		posY += motionY * 1.2F;
		posZ += motionZ * 1.2F;
		setPosition(posX, posY, posZ);
		setIsCritical(superPowered);
		setThrowableHeading(motionX, motionY, motionZ, superPowered ? 4.0F : 2.0F, superPowered ? 0.1F : 2.0F);
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
	
	public void createCrater()
	{
		if (worldObj.isRemote || !inGround || isInWater()) return;
		
		setDead();
		
		float f = getIsCritical() ? 5.0F : 2.5F;
		PhysHelper.createAdvancedExplosion(worldObj, this, posX, posY, posZ, f, BalkonsWeaponMod.instance.modConfig.cannonDoesBlockDamage, true);
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
		if (entity.attackEntityFrom(damagesource, 30))
		{
			worldObj.playSoundAtEntity(this, "random.damage.hurtflesh", 1.0F, 1.2F / (rand.nextFloat() * 0.4F + 0.7F));
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
		
		createCrater();
	}
	
	@Override
	public boolean canBeCritical()
	{
		return true;
	}
	
	@Override
	public float getAirResistance()
	{
		return 0.98F;
	}
	
	@Override
	public float getGravity()
	{
		return 0.04F;
	}
	
	@Override
	public ItemStack getPickupItem()
	{
		return new ItemStack(BalkonsWeaponMod.cannonBall, 1);
	}
	
	@Override
	public float getShadowSize()
	{
		return 0.5F;
	}
}
