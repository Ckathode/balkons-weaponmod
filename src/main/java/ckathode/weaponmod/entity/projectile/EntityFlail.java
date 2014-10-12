package ckathode.weaponmod.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.ItemFlail;

public class EntityFlail extends EntityMaterialProjectile
{
	public boolean	isSwinging;
	private float	flailDamage;
	private double	distanceTotal;
	private double	distanceX;
	private double	distanceY;
	private double	distanceZ;
	
	public EntityFlail(World world)
	{
		super(world);
		ignoreFrustumCheck = true;
		flailDamage = 1F;
		distanceTotal = 0D;
		distanceX = distanceY = distanceZ = 0D;
	}
	
	public EntityFlail(World world, double d, double d1, double d2)
	{
		this(world);
		setPosition(d, d1, d2);
	}
	
	public EntityFlail(World world, EntityLivingBase entityliving, ItemStack itemstack)
	{
		this(world);
		shootingEntity = entityliving;
		setPickupModeFromEntity(entityliving);
		setThrownItemStack(itemstack);
		distanceTotal = 0F;
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.4D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		swing();
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (shootingEntity != null)
		{
			distanceX = shootingEntity.posX - posX;
			distanceY = shootingEntity.posY - posY;
			distanceZ = shootingEntity.posZ - posZ;
			
			distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
			
			if (distanceTotal > 3.0D)
			{
				returnToOwner(shootingEntity, true);
			}
			
			if (shootingEntity instanceof EntityPlayer)
			{
				ItemStack itemstack = ((EntityPlayer) shootingEntity).getCurrentEquippedItem();
				if (itemstack == null || thrownItem != null && itemstack.getItem() != thrownItem.getItem())
				{
					pickUpByOwner();
				}
			}
		}
		
		if (inGround)
		{
			inGround = false;
			return;
		}
		
		returnToOwner(shootingEntity, false);
	}
	
	public void returnToOwner(Entity entity, boolean looseFromGround)
	{
		if (entity == null) return;
		
		if (looseFromGround)
		{
			inGround = false;
		}
		
		double targetPosX = shootingEntity.posX;
		double targetPosY = shootingEntity.boundingBox.minY + 0.4F;
		double targetPosZ = shootingEntity.posZ;
		
		float f = 27F;
		float f1 = 2F;
		targetPosX += -Math.sin(((shootingEntity.rotationYaw + f) / 180F) * Math.PI) * Math.cos((shootingEntity.rotationPitch / 180F) * Math.PI) * f1;
		targetPosZ += Math.cos(((shootingEntity.rotationYaw + f) / 180F) * Math.PI) * Math.cos((shootingEntity.rotationPitch / 180F) * Math.PI) * f1;
		
		distanceX = targetPosX - posX;
		distanceY = targetPosY - posY;
		distanceZ = targetPosZ - posZ;
		
		distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
		
		if (distanceTotal > 3.0D)
		{
			posX = targetPosX;
			posY = targetPosY;
			posZ = targetPosZ;
		} else if (distanceTotal > 2.5D)
		{
			isSwinging = false;
			motionX *= -0.5F;
			motionY *= -0.5F;
			motionZ *= -0.5F;
		}
		
		if (!isSwinging)
		{
			float f6 = 0.2F;
			motionX = distanceX * f6 * (distanceTotal);
			motionY = distanceY * f6 * (distanceTotal);
			motionZ = distanceZ * f6 * (distanceTotal);
		}
	}
	
	public void pickUpByOwner()
	{
		setDead();
		if (shootingEntity instanceof EntityPlayer && thrownItem != null)
		{
			((ItemFlail) thrownItem.getItem()).setThrown((EntityPlayer) shootingEntity, false);
		}
	}
	
	public void swing()
	{
		if (isSwinging) return;
		worldObj.playSoundAtEntity(shootingEntity, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
		motionX = -MathHelper.sin(((shootingEntity.rotationYaw) / 180F) * 3.141593F) * MathHelper.cos((shootingEntity.rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((shootingEntity.rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos(((shootingEntity.rotationYaw) / 180F) * 3.141593F) * MathHelper.cos((shootingEntity.rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(motionX, motionY, motionZ, 0.75F, 3.0F);
		isSwinging = true;
		inGround = false;
	}
	
	@Override
	public void onEntityHit(Entity entity)
	{
		if (entity == shootingEntity) return;
		
		DamageSource damagesource = null;
		if (shootingEntity instanceof EntityLivingBase)
		{
			damagesource = DamageSource.causeMobDamage((EntityLivingBase) shootingEntity);
		} else
		{
			damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
		}
		if (entity.attackEntityFrom(damagesource, flailDamage + extraDamage))
		{
			playHitSound();
			returnToOwner(shootingEntity, true);
		} else
		{
			bounceBack();
		}
	}
	
	@Override
	public void bounceBack()
	{
		motionX *= -0.8D;
		motionY *= -0.8D;
		motionZ *= -0.8D;
		rotationYaw += 180F;
		prevRotationYaw += 180F;
		ticksInAir = 0;
	}
	
	@Override
	public void playHitSound()
	{
		if (inGround) return;
		
		worldObj.playSoundAtEntity(this, "damage.hurtflesh", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
	}
	
	@Override
	public void setThrownItemStack(ItemStack itemstack)
	{
		if (!(itemstack.getItem() instanceof ItemFlail)) return;
		
		super.setThrownItemStack(itemstack);
		flailDamage = ((ItemFlail) itemstack.getItem()).getFlailDamage();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setFloat("fDmg", flailDamage);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		flailDamage = nbttagcompound.getFloat("fDmg");
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer entityplayer)
	{
	}
	
	@Override
	public int getMaxArrowShake()
	{
		return 0;
	}
	
	@Override
	public float getShadowSize()
	{
		return 0.2F;
	}
}
