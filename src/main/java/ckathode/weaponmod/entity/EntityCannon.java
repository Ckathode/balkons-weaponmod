package ckathode.weaponmod.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;

public class EntityCannon extends EntityBoat
{
	public EntityCannon(World world)
	{
		super(world);
		preventEntitySpawning = true;
		rotationPitch = -20F;
		setRotation(rotationYaw, rotationPitch);
		setSize(1.5F, 1.0F);
		yOffset = height / 2.0F;
	}
	
	public EntityCannon(World world, double d, double d1, double d2)
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
		dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		dataWatcher.addObject(21, Short.valueOf((short) 0));
		dataWatcher.addObject(22, Byte.valueOf((byte) 0));
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
	public double getMountedYOffset()
	{
		return 0.15D;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float damage)
	{
		if (worldObj.isRemote || isDead) return true;
		if (damagesource instanceof EntityDamageSourceIndirect)
		{
			if (((EntityDamageSource) damagesource).getEntity() == riddenByEntity) return true;
		} else if (damagesource instanceof EntityDamageSource)
		{
			if (((EntityDamageSource) damagesource).damageType.equals("player"))
			{
				EntityPlayer player = (EntityPlayer) (((EntityDamageSource) damagesource).getEntity());
				if (player.inventory.getCurrentItem() == null)
				{
					dropItem(BalkonsWeaponMod.cannon, 1);
					if (isLoaded() || isLoading())
					{
						dropItem(BalkonsWeaponMod.cannonBall, 1);
						dropItem(Items.gunpowder, 1);
					}
					setDead();
					return true;
				}
			}
		}
		
		setRockDirection(-getRockDirection());
		setTimeSinceHit(10);
		setCurrentDamage(getCurrentDamage() + (int) damage * 5);
		setBeenAttacked();
		
		if (getCurrentDamage() > 100)
		{
			if (riddenByEntity != null)
			{
				riddenByEntity.mountEntity(this);
			}
			
			for (int j = 0; j < 6; j++)
			{
				dropItemWithChance(Items.iron_ingot, (int) damage, 1);
			}
			dropItemWithChance(Items.flint, (int) damage, 1);
			dropItemWithChance(Item.getItemFromBlock(Blocks.log), (int) damage, 1);
			
			if (isLoaded() || isLoading())
			{
				dropItem(BalkonsWeaponMod.cannonBall, 1);
				dropItem(Items.gunpowder, 1);
			}
			setDead();
		}
		return true;
	}
	
	public void dropItemWithChance(Item item, int chance, int amount)
	{
		if (rand.nextInt(chance) < 10)
		{
			dropItem(item, amount);
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
		onEntityUpdate();
	}
	
	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
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
		
		motionY -= 0.1D;
		if (onGround)
		{
			motionX *= 0.1D;
			motionZ *= 0.1D;
		}
		motionX *= 0.98D;
		motionY *= 0.98D;
		motionZ *= 0.98D;
		if (!onGround)
		{
			fallDistance += -motionY;
		}
		
		if (riddenByEntity != null)
		{
			float yaw = riddenByEntity.rotationYaw;
			float pitch = riddenByEntity.rotationPitch;
			/*prevRotationYaw = */rotationYaw = (yaw - 180F) % 360F;
			/*prevRotationPitch = */rotationPitch = pitch;
		}
		setRotation(rotationYaw, rotationPitch);
		moveEntity(motionX, motionY, motionZ);
		
		@SuppressWarnings("unchecked")
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.2D, 0.0D, 0.2D));
		if (list != null && !list.isEmpty())
		{
			for (Entity entity : list)
			{
				if (entity != riddenByEntity && entity.canBePushed())
				{
					entity.applyEntityCollision(this);
				}
			}
		}
		
		if (riddenByEntity != null)
		{
			if (riddenByEntity.isDead)
			{
				riddenByEntity = null;
			}
		}
		
		if (isLoading())
		{
			setLoadTimer(getLoadTimer() - 1);
			handleReloadTime();
		}
	}
	
	@Override
	protected void fall(float f)
	{
		super.fall(f);
		int i = MathHelper.floor_float(f);
		i *= 2;
		attackEntityFrom(DamageSource.fall, i);
	}
	
	public void handleReloadTime()
	{
		int l = getLoadTimer();
		if (l > 0)
		{
			if (l == 80 || l == 70 || l == 60)
			{
				worldObj.playSoundAtEntity(this, "tile.piston.in", 0.5F, 1.2F / (rand.nextFloat() * 0.8F + 0.6F));
			} else if (l == 40)
			{
				worldObj.playSoundAtEntity(this, "random.breath", 0.7F, 1.2F / (rand.nextFloat() * 0.2F + 10.0F));
			}
		} else
		{
			setReloadInfo(true, 0);
		}
	}
	
	public void fireCannon()
	{
		if (!isLoaded()) return;
		if (!worldObj.isRemote)
		{
			EntityCannonBall entitycannonball = new EntityCannonBall(worldObj, this, isSuperPowered());
			worldObj.spawnEntityInWorld(entitycannonball);
		}
		
		setReloadInfo(false, 0);
		
		fireEffects();
	}
	
	public void fireEffects()
	{
		worldObj.playSoundAtEntity(this, "random.explode", 8.0F, 1.0F / (rand.nextFloat() * 0.8F + 0.9F));
		worldObj.playSoundAtEntity(this, "ambient.weather.thunder", 8.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.6F));
		
		float yaw = (float) Math.toRadians(rotationYaw);
		double d = -MathHelper.sin(yaw) * -1F;
		double d1 = MathHelper.cos(yaw) * -1F;
		for (int i = 0; i < 20; i++)
		{
			worldObj.spawnParticle("smoke", posX + d + rand.nextDouble() * 0.5D - 0.25D, posY + rand.nextDouble() * 0.5D, posZ + d1 + rand.nextDouble() * 0.5D - 0.25D, rand.nextDouble() * 0.1D - 0.05D, rand.nextDouble() * 0.1D - 0.05D, rand.nextDouble() * 0.1D - 0.05D);
		}
		
		if (riddenByEntity != null)
		{
			riddenByEntity.rotationPitch += 10F;
		}
		attackEntityFrom(DamageSource.generic, 2);
	}
	
	public void setReloadInfo(boolean loaded, int reloadtime)
	{
		setLoaded(loaded);
		setLoadTimer(reloadtime);
	}
	
	public void startLoadingCannon()
	{
		if (isLoaded() && !isLoading()) return;
		setReloadInfo(false, 100);
	}
	
	@Override
	public void updateRiderPosition()
	{
		if (riddenByEntity != null)
		{
			float f = 0.85F;
			double d = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * f;
			double d1 = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * f;
			riddenByEntity.setPosition(posX + d, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + d1);
		}
	}
	
	@Override
	public float getShadowSize()
	{
		return 1.0F;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setFloat("falld", fallDistance);
		nbttagcompound.setBoolean("load", isLoaded());
		nbttagcompound.setShort("ldtime", (short) getLoadTimer());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		setPosition(posX, posY, posZ);
		setRotation(rotationYaw, rotationPitch);
		fallDistance = nbttagcompound.getFloat("falld");
		setLoaded(nbttagcompound.getBoolean("load"));
		setLoadTimer(nbttagcompound.getShort("ldtime"));
	}
	
	@Override
	public boolean interactFirst(EntityPlayer entityplayer)
	{
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		Item item = itemstack == null ? null : itemstack.getItem();
		if (item == BalkonsWeaponMod.cannonBall && !isLoaded() && !isLoading())
		{
			if (entityplayer.inventory.consumeInventoryItem(Items.gunpowder))
			{
				if (entityplayer.inventory.consumeInventoryItem(BalkonsWeaponMod.cannonBall))
				{
					startLoadingCannon();
					return false;
				}
				dropItem(Items.gunpowder, 1);
				return true;
			}
		}
		
		if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != entityplayer) return true;
		if (!worldObj.isRemote)
		{
			entityplayer.mountEntity(this);
		}
		return true;
	}
	
	@Override
	public void onStruckByLightning(EntityLightningBolt entitylightningbolt)
	{
		attackEntityFrom(null, 100);
		setSuperPowered(true);
	}
	
	public void setLoaded(boolean flag)
	{
		dataWatcher.updateObject(20, Byte.valueOf((byte) (flag ? 1 : 0)));
	}
	
	public void setLoadTimer(int time)
	{
		dataWatcher.updateObject(21, Short.valueOf((short) time));
	}
	
	public void setSuperPowered(boolean flag)
	{
		dataWatcher.updateObject(22, Byte.valueOf((byte) (flag ? 1 : 0)));
	}
	
	public boolean isLoading()
	{
		return getLoadTimer() > 0;
	}
	
	public boolean isLoaded()
	{
		return dataWatcher.getWatchableObjectByte(20) != 0;
	}
	
	public int getLoadTimer()
	{
		return dataWatcher.getWatchableObjectShort(21);
	}
	
	public boolean isSuperPowered()
	{
		return dataWatcher.getWatchableObjectByte(22) != 0;
	}
	
	@Override
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
	
	@Override
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
