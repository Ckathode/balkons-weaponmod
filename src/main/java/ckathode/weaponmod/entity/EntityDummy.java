package ckathode.weaponmod.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;

public class EntityDummy extends Entity
{
	private int	durability;
	
	public EntityDummy(World world)
	{
		super(world);
		preventEntitySpawning = true;
		rotationPitch = -20F;
		setRotation(rotationYaw, rotationPitch);
		setSize(0.5F, 1.9F);
		yOffset = 0.41F;
		durability = 50;
	}
	
	public EntityDummy(World world, double d, double d1, double d2)
	{
		this(world);
		setPosition(d, d1 + yOffset, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
	}
	
	@Override
	protected void entityInit()
	{
		dataWatcher.addObject(17, Integer.valueOf(0));
		dataWatcher.addObject(18, Byte.valueOf((byte) 1));
		dataWatcher.addObject(19, Integer.valueOf(0));
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity entity)
	{
		return entity.boundingBox;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return boundingBox;
	}
	
	@Override
	public boolean canBePushed()
	{
		return false;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float damage)
	{
		if (worldObj.isRemote || isDead || damage <= 0) return false;
		
		setRockDirection(-getRockDirection());
		setTimeSinceHit(10);
		int i = getCurrentDamage();
		i += damage * 5;
		if (i > 50)
		{
			i = 50;
		}
		setCurrentDamage(i);
		setBeenAttacked();
		
		if (!(damagesource instanceof EntityDamageSource))
		{
			durability -= damage;
		} else if (damagesource instanceof WeaponDamageSource)
		{
			Entity entity = ((WeaponDamageSource) damagesource).getProjectile();
			
			if (Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ) > 0.5D)
			{
				entity.motionX *= 0.1F;
				entity.motionY *= 0.1F;
				entity.motionZ *= 0.1F;
				playRandomHitSound();
			} else
			{
				entity.motionX = rand.nextFloat() - 0.5F;
				entity.motionY = rand.nextFloat() - 0.5F;
				entity.motionZ = rand.nextFloat() - 0.5F;
			}
		} else
		{
			playRandomHitSound();
		}
		
		if (durability <= 0)
		{
			dropAsItem(true);
		}
		
		setBeenAttacked();
		
		return false;
	}
	
	public void playRandomHitSound()
	{
		int i = rand.nextInt(2);
		if (i == 0)
		{
			worldObj.playSoundAtEntity(this, "step.cloth", 0.7F, 1F / rand.nextFloat() * 0.2F + 0.4F);
		} else if (i == 1)
		{
			worldObj.playSoundAtEntity(this, "step.wood", 0.7F, 1F / rand.nextFloat() * 0.2F + 0.2F);
		}
	}
	
	@Override
	public void performHurtAnimation()
	{
		setRockDirection(-getRockDirection());
		setTimeSinceHit(10);
		setCurrentDamage(getCurrentDamage() + 10);
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
		return !isDead;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		int i = getTimeSinceHit();
		if (i > 0)
		{
			setTimeSinceHit(i - 1);
		}
		i = getCurrentDamage();
		if (i > 0)
		{
			setCurrentDamage(i - rand.nextInt(2));
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		
		if (onGround)
		{
			motionX = 0D;
			motionY = 0D;
			motionZ = 0D;
		} else
		{
			motionX *= 0.99D;
			motionZ *= 0.99D;
			motionY -= 0.05D;
			fallDistance += -motionY;
		}
		setRotation(rotationYaw, rotationPitch);
		moveEntity(0D, motionY, 0D);
		
		@SuppressWarnings("unchecked")
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.2D, 0.0D, 0.2D));
		if (list != null && list.size() > 0)
		{
			for (int j1 = 0; j1 < list.size(); j1++)
			{
				Entity entity = list.get(j1);
				if (entity != riddenByEntity && entity.canBePushed())
				{
					entity.applyEntityCollision(this);
				}
			}
		}
	}
	
	@Override
	protected void fall(float f)
	{
		super.fall(f);
		if (!onGround) return;
		int i = MathHelper.floor_float(f);
		attackEntityFrom(DamageSource.fall, i);
	}
	
	@Override
	public float getShadowSize()
	{
		return 1.0F;
	}
	
	public void dropAsItem(boolean destroyed)
	{
		if (worldObj.isRemote) return;
		if (destroyed)
		{
			for (int i = 0; i < rand.nextInt(8); i++)
			{
				dropItem(Items.leather, 1);
			}
		} else
		{
			dropItem(BalkonsWeaponMod.dummy, 1);
		}
		setDead();
	}
	
	@Override
	public boolean interactFirst(EntityPlayer entityplayer)
	{
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack == null)
		{
			dropAsItem(false);
			return true;
		} else if (!(itemstack.getItem() instanceof IItemWeapon) && !(itemstack.getItem() instanceof ItemSword) && !(itemstack.getItem() instanceof ItemBow))
		{
			dropAsItem(false);
			return true;
		}
		return false;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		setPosition(posX, posY, posZ);
		setRotation(rotationYaw, rotationPitch);
	}
	
	public void setTimeSinceHit(int i)
	{
		dataWatcher.updateObject(17, Integer.valueOf(i));
	}
	
	public void setRockDirection(int i)
	{
		dataWatcher.updateObject(18, Byte.valueOf((byte) i));
	}
	
	public void setCurrentDamage(int i)
	{
		dataWatcher.updateObject(19, Integer.valueOf(i));
	}
	
	public int getTimeSinceHit()
	{
		return dataWatcher.getWatchableObjectInt(17);
	}
	
	public int getRockDirection()
	{
		return dataWatcher.getWatchableObjectByte(18);
	}
	
	public int getCurrentDamage()
	{
		return dataWatcher.getWatchableObjectInt(19);
	}
}
