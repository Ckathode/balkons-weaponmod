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
	public boolean	isSwinging2;
	private float	flailDamage;
	private double	distanceTotal;
	private double	rotateRadius;
	private double	distanceX;
	private double	distanceY;
	private double	distanceZ;
	public int		flag;
	
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
	
	public EntityFlail(World world, EntityLivingBase entityliving, ItemStack itemstack ,int throwType)
	{
		this(world);
		shootingEntity = entityliving;
		setPickupModeFromEntity(entityliving);
		setThrownItemStack(itemstack);
		distanceTotal = 0F;
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX += MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.3F;
		posY -= 0.4D;
		posZ -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.3F;
		setPosition(posX, posY, posZ);
		flag = throwType;
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
		if (ticksInAir > 5 && isSwinging)
		{
			swing();
			
		}
		if (shootingEntity != null)
		{
			distanceX = shootingEntity.posX - posX;
			distanceY = shootingEntity.posY - posY;
			distanceZ = shootingEntity.posZ - posZ;
			
			distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
			
			
			if (shootingEntity instanceof EntityPlayer)
			{
				ItemStack itemstack = ((EntityPlayer) shootingEntity).getCurrentEquippedItem();
				if (itemstack == null || thrownItem != null && itemstack.getItem() != thrownItem.getItem())
				{
					pickUpByOwner();
				}
			}
		}
		else
		{
			setDead();
		}
		
		if (inGround)
		{
			inGround = false;
		}
		
		returnToOwner2(shootingEntity, true);
	}
	
	public void returnToOwner2(Entity entity, boolean looseFromGround)
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
		
		if (flag == 0){
			if (distanceTotal > 15.0D && (isSwinging || isSwinging2))
			{
				posX = targetPosX;
				posY = targetPosY;
				posZ = targetPosZ;
			}
			else if(distanceTotal > 5.0D && (isSwinging || isSwinging2))
			{
				double dx;
				double dy;
				double dz;
				dx = posX - shootingEntity.posX;
				dy = posY - shootingEntity.posY - shootingEntity.getEyeHeight();
				dz = posZ - shootingEntity.posZ;
				
				
				motionX -= 0.5F * dx;
				motionY -= 0.5F * dy;
				motionZ -= 0.5F * dz;
			}
		}
		else if(flag == 1 && ticksInAir > 0 && ticksInAir < 400)
		{
			if(ticksInAir <= 5){
				rotateRadius = distanceTotal;
			}
			else if(ticksInAir > 5 && ticksInAir < 400){
				double x =distanceX / distanceTotal;
				double y =distanceY / distanceTotal;
				double z =distanceZ / distanceTotal;
				// x1=-z;y1 = 0;z1 = x;相差向量与垂直向量的向量积
				double x2 = 0 * z - x * y;
				double y2 = x * x - -z * z;
				double z2 = -z * y - 0 * x;
				//相差向量产生的法向量
				double x3 = y2 * z - z2 * y;
				double y3 = z2 * x - x2 * z;
				double z3 = x2 * y - y2 * x;
				float f6 = 0.2F;
				motionX = x3 * (1F) + distanceX * f6 * (distanceTotal - rotateRadius);
				motionY = y3 * (1F) + distanceY * f6 * (distanceTotal - rotateRadius);
				motionZ = z3 * (1F) + distanceZ * f6 * (distanceTotal - rotateRadius);
			}
		}
		
		
		if (!isSwinging && !isSwinging2)
			{if (distanceTotal > 5.0D)
			{
				posX = targetPosX;
				posY = targetPosY;
				posZ = targetPosZ;
				motionX = 0;
				motionY = 0;
				motionZ = 0;
			}
			else{float f6 = 0.2F;
			motionX = distanceX * f6 * (distanceTotal);
			motionY = distanceY * f6 * (distanceTotal);
			motionZ = distanceZ * f6 * (distanceTotal);}
		}
	}
	/*
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
		
		if (distanceTotal > 5.0D && inGround)
		{
			posX = targetPosX;
			posY = targetPosY;
			posZ = targetPosZ;
		} else if (distanceTotal > 2.5D)
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
			
			motionX -= 0.5F * dx;
			motionY -= 0.5F * dy;
			motionZ -= 0.5F * dz;
		}
		
		if (!isSwinging)
		{
			float f6 = 0.2F;
			motionX = distanceX * f6 * (distanceTotal);
			motionY = distanceY * f6 * (distanceTotal);
			motionZ = distanceZ * f6 * (distanceTotal);
		}
	}
	*/
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
		if (flag == 0)
		{
			if (!isSwinging){
				
			worldObj.playSoundAtEntity(shootingEntity, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
			motionX = MathHelper.sin((shootingEntity.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((shootingEntity.rotationPitch / 180F) * 3.141593F);
			motionY = MathHelper.sin((shootingEntity.rotationPitch / 180F) * 3.141593F);
			motionZ = -MathHelper.cos((shootingEntity.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((shootingEntity.rotationPitch / 180F) * 3.141593F);
			setThrowableHeading(motionX, motionY, motionZ, 1.5F, 0);
			isSwinging = true;
			inGround = false;
			ticksInAir = 0;
			}else if (!isSwinging2)
			{
				motionX = -MathHelper.sin((shootingEntity.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((shootingEntity.rotationPitch / 180F) * 3.141593F);
				motionY = -MathHelper.sin((shootingEntity.rotationPitch / 180F) * 3.141593F);
				motionZ = MathHelper.cos((shootingEntity.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((shootingEntity.rotationPitch / 180F) * 3.141593F);
				setThrowableHeading(motionX, motionY, motionZ, 3.0F, 0);
				inGround = false;
				ticksInAir = 0;
				isSwinging2 = true;
			}else if (ticksInAir > 5 && isSwinging2){
				ticksInAir = 0;
				isSwinging = false;
				isSwinging2 = false;
			}
		}
		else if (flag == 1)
		{
			if (!isSwinging){
			
				worldObj.playSoundAtEntity(shootingEntity, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
				motionX = MathHelper.sin((shootingEntity.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((shootingEntity.rotationPitch / 180F) * 3.141593F);
				motionY = MathHelper.sin((shootingEntity.rotationPitch / 180F) * 3.141593F);
				motionZ = -MathHelper.cos((shootingEntity.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((shootingEntity.rotationPitch / 180F) * 3.141593F);
				setThrowableHeading(motionX, motionY, motionZ, 0.5F, 0);
				isSwinging = true;
				isSwinging2 = false;
				inGround = false;
				ticksInAir = 0;
			}
			else if(ticksInAir > 400){
				isSwinging = false;
				isSwinging = false;
				ticksInAir = 0;
			}
		}
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
		if (entity.attackEntityFrom(damagesource, flailDamage * (1F + extraDamage)))
		{
			playHitSound();
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
	public float getGravity()
	{
		return 0F;
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
