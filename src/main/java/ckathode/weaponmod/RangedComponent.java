package ckathode.weaponmod.item;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.WeaponModAttributes;
import ckathode.weaponmod.entity.projectile.EntityProjectile;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class RangedComponent extends AbstractWeaponComponent
{
	protected static final int	MAX_DELAY	= 72000;
	
	protected static final int	MANUAL	= 0, SEMI_AUTO = 1, AUTO = 2;
	
	public final Item.ToolMaterial	weaponMaterial;
	
	public int ticksInHold;
	
	public static boolean isReloaded(ItemStack itemstack)
	{
		return ReloadHelper.getReloadState(itemstack) >= ReloadHelper.STATE_RELOADED;
	}
	
	public static boolean isReadyToFire(ItemStack itemstack)
	{
		return ReloadHelper.getReloadState(itemstack) == ReloadHelper.STATE_READY;
	}
	
	public static void setReloadState(ItemStack itemstack, int state)
	{
		ReloadHelper.setReloadState(itemstack, state);
	}
	
	public final RangedSpecs	rangedSpecs;
	
	public RangedComponent(RangedSpecs rangedspecs, Item.ToolMaterial toolmaterial)
	{
		rangedSpecs = rangedspecs;
		weaponMaterial = toolmaterial;
	}
	
	@Override
	protected void onSetItem()
	{
	}
	
	@Override
	public void setThisItemProperties()
	{
		if (weaponMaterial == null)
		{
			item.setMaxDamage(rangedSpecs.durabilityBase);
		} else
		{
			item.setMaxDamage((int) (rangedSpecs.durabilityBase + weaponMaterial.getMaxUses()));
		}
		item.setMaxStackSize(rangedSpecs.stackSize);
	}
	
	@Override
	public float getEntityDamageMaterialPart()
	{
		return 0;
	}
	
	@Override
	public float getEntityDamage()
	{
		return 0;
	}
	
	@Override
	public float getBlockDamage(ItemStack itemstack, Block block)
	{
		return 0;
	}
	
	@Override
	public boolean canHarvestBlock(Block block)
	{
		return false;
	}

	@Override
	public ItemStack onRightClickEntity(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return itemstack;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int j, int k, int l, EntityLivingBase entityliving)
	{
		return false;
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return false;
	}
	
	@Override
	public int getAttackDelay(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return 0;
	}
	
	@Override
	public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return 0;
	}

	@Override
	public int getItemEnchantability()
	{
		return weaponMaterial == null ? 1 : weaponMaterial.getEnchantability();
	}
	
	@Override
	public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap)
	{
		multimap.put(WeaponModAttributes.RELOAD_TIME.getAttributeUnlocalizedName(), new AttributeModifier(weapon.getUUID(), "Weapon reloadtime modifier", rangedSpecs.getReloadTime(), 0));
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		return false;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		int state = ReloadHelper.getReloadState(itemstack);
		if (state == ReloadHelper.STATE_NONE)
		{
			return EnumAction.block;
		} else if (state == ReloadHelper.STATE_READY)
		{
			return EnumAction.bow;
		}
		return EnumAction.none;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return MAX_DELAY;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (itemstack.stackSize <= 0 || entityplayer.isUsingItem()) return itemstack;
		
		if(getReloadType(itemstack)==MANUAL)
		{
			
			if (hasAmmo(itemstack, world, entityplayer)) //Check can reload
			{
				if (isReadyToFire(itemstack))
				{
					//Start aiming weapon to fire
					soundCharge(itemstack, world, entityplayer);
					entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
				} else
				{
					//Begin reloading
					entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
				}
			} else
			{
				//Can't reload; no ammo
				soundEmpty(itemstack, world, entityplayer);
				setReloadState(itemstack, ReloadHelper.STATE_NONE);
			}
			
		}
		else if(getReloadType(itemstack)==SEMI_AUTO)
		{
			if (hasAmmoAndConsume(itemstack, world, entityplayer)) //Check can reload
			{
				if (isReadyToFire(itemstack))
				{
					//Start aiming weapon to fire
					soundCharge(itemstack, world, entityplayer);
					entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
				} else
				{
					//Begin reloading
					soundEmpty(itemstack, world, entityplayer);
				}
			} else
			{
				//Can't reload; no ammo
				soundEmpty(itemstack, world, entityplayer);
				setReloadState(itemstack, ReloadHelper.STATE_NONE);
			}
		}
		

		else if(getReloadType(itemstack)==AUTO)
		{
			if (hasAmmoAndConsume(itemstack, world, entityplayer)) //Check can reload
			{
				if (isReadyToFire(itemstack))
				{
					//Start aiming weapon to fire
					soundCharge(itemstack, world, entityplayer);
					fire(itemstack, world, entityplayer, 0);
				}
			} else
			{
				//Can't reload; no ammo
				soundEmpty(itemstack, world, entityplayer);
				setReloadState(itemstack, ReloadHelper.STATE_NONE);
			}
		}
		
		return itemstack;
	}
	
	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count)
	{
		if (ReloadHelper.getReloadState(itemstack) == ReloadHelper.STATE_NONE && getMaxItemUseDuration(itemstack) - count >= getReloadDuration(itemstack))
		{
			effectReloadDone(itemstack, entityplayer.worldObj, entityplayer);
			entityplayer.clearItemInUse();
			setReloadState(itemstack, ReloadHelper.STATE_RELOADED);
		}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		if (!isReloaded(itemstack)) return;
		if (isReadyToFire(itemstack))
		{
			if (hasAmmoAndConsume(itemstack, world, entityplayer))
			{
				fire(itemstack, world, entityplayer, i);
			}
			setReloadState(itemstack, ReloadHelper.STATE_NONE);
		} else
		{
			setReloadState(itemstack, ReloadHelper.STATE_READY);
		}
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
		int state = ReloadHelper.getReloadState(itemstack);
		if (entity instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (state == ReloadHelper.STATE_NONE && flag && hasAmmo(itemstack, world, entityplayer) && getReloadType(itemstack) >= SEMI_AUTO)
    		{

    			if (ticksInHold <= getReloadDuration(itemstack)) ticksInHold++;
    			
    		} else
    		{
    			
    			ticksInHold = 0;
    			
    		}
        }
		
		if (ticksInHold >= getReloadDuration(itemstack))
		{
			setReloadState(itemstack, ReloadHelper.STATE_READY);
			
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldRotateAroundWhenRendering()
	{
		return false;
	}
	
	public void soundEmpty(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		world.playSoundAtEntity(entityplayer, "random.click", 1.0F, 1.0F / 0.8F);
	}
	
	public void soundCharge(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
	}
	
	public final void postShootingEffects(ItemStack itemstack, EntityPlayer entityplayer, World world)
	{
		effectPlayer(itemstack, entityplayer, world);
		effectShoot(world, entityplayer.posX, entityplayer.posY, entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch);
	}
	
	public abstract void effectReloadDone(ItemStack itemstack, World world, EntityPlayer entityplayer);
	
	public abstract void fire(ItemStack itemstack, World world, EntityPlayer entityplayer, int i);
	
	public abstract void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world);
	
	public abstract void effectShoot(World world, double x, double y, double z, float yaw, float pitch);
	
	public void applyProjectileEnchantments(EntityProjectile entity, ItemStack itemstack)
	{
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0)
		{
			entity.setPickupMode(EntityProjectile.NO_PICKUP);
		}
		
		int damage = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
		if (damage > 0)
		{
			entity.setExtraDamage(damage/5);
		}
		
		int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
		if (knockback > 0)
		{
			entity.setKnockbackStrength(knockback);
		}
		
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
		{
			entity.setFire(100);
		}
	}
	
	public int getReloadDuration(ItemStack itemstack)
	{
		return rangedSpecs.getReloadTime();
	}
	
	public Item getAmmoItem()
	{
		return rangedSpecs.getAmmoItem();
	}
	
	public boolean hasAmmoAndConsume(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0 || entityplayer.inventory.consumeInventoryItem(getAmmoItem());
	}
	
	public boolean hasAmmo(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0 || entityplayer.inventory.hasItem(getAmmoItem());
	}
	
	public int getReloadType(ItemStack itemstack)
	{
		return MANUAL;
	}
	
	public float getFOVMultiplier(int ticksinuse, EntityPlayer entityplayer)
	{
		float f1 = ticksinuse / getMaxAimTimeTicks();
		
		if (f1 > 1.0F)
		{
			f1 = 1.0F;
		} else
		{
			f1 *= f1;
		}
		
		return 1.0F - f1 * getMaxZoom(entityplayer);
	}
	
	protected float getMaxAimTimeTicks()
	{
		return 20.0f;
	}
	
	protected float getMaxZoom(EntityPlayer entityplayer)
	{
		return 0.15f;
	}
	
	public static enum RangedSpecs
	{
		BLOWGUN("weaponmod:dart", "blowgun", 250, 1),
		BLOWGUNTEEMO("weaponmod:dart", "blowgunteemo", 0, 1),
		CROSSBOW("weaponmod:bolt", "crossbow", 250, 1),
		MUSKET("weaponmod:bullet", "musket", 80, 1),
		BLUNDERBUSS("weaponmod:shot", "blunderbuss", 80, 1),
		FLINTLOCK("weaponmod:bullet", "flintlock", 80, 1),
		NAILGUN("weaponmod:screw", "nailgun", 250, 1),
		NAILGUNMK2("weaponmod:screw", "nailgunMk-2", 350, 1),
		SNIPERRIFLE("weaponmod:rifle-bullet", "sniperrifle", 64, 1),
		SHOTGUN("weaponmod:buckshot", "shotgun", 64, 1),
		ROCKET("weaponmod:rocket-shell", "rocket", 16, 1),
		ROCKETWOODEN("weaponmod:rocket-shell", "rocket-wooden", 1, 1),
		NONE(null, null, 0, 1);
		
		RangedSpecs(String ammoitemtag, String reloadtimetag, int durability, int stacksize)
		{
			durabilityBase = durability;
			ammoItemTag = ammoitemtag;
			reloadTimeTag = reloadtimetag;
			stackSize = stacksize;
			ammoItem = null;
			reloadTime = -1;
		}
		
		public int getReloadTime()
		{
			if (reloadTime < 0 && BalkonsWeaponMod.instance != null)
			{
				reloadTime = BalkonsWeaponMod.instance.modConfig.getReloadTime(reloadTimeTag);
				BalkonsWeaponMod.modLog.debug("Found reaload time " + reloadTime + " for " + reloadTimeTag + " @" + this);
			}
			return reloadTime;
		}
		
		public Item getAmmoItem()
		{
			if (ammoItem == null && ammoItemTag != null)
			{
				ammoItem = (Item) Item.itemRegistry.getObject(ammoItemTag);
				BalkonsWeaponMod.modLog.debug("Found item " + ammoItem + " for " + ammoItemTag + " @" + this);
				ammoItemTag = null;
			}
			return ammoItem;
		}

		private int			reloadTime;
		private Item		ammoItem;
		private String		ammoItemTag;
		public final String	reloadTimeTag;
		public final int	durabilityBase, stackSize;
	}
}
