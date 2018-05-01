package ckathode.weaponmod.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.item.MeleeComponent.MeleeSpecs;

public class ItemMusket extends ItemShooter
{
	protected Item	bayonetItem;
	private int		bayonetDurability;
	
	public ItemMusket(String id, MeleeComponent meleecomponent, Item bayonetitem)
	{
		super(id, new RangedCompMusket(), meleecomponent);
		bayonetItem = bayonetitem;
		if (meleecomponent.meleeSpecs != MeleeSpecs.NONE && meleecomponent.weaponMaterial != null)
		{
			bayonetDurability = meleecomponent.meleeSpecs.durabilityBase + (int) (meleecomponent.weaponMaterial.getMaxUses() * meleecomponent.meleeSpecs.durabilityMult);
		}
	}
	
	public boolean hasBayonet()
	{
		return bayonetItem != null;
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime)
		{
			float kb = meleeComponent.getKnockBack(itemstack, entityliving, attacker);
			//if (entityliving.onGround)
			{
				PhysHelper.knockBack(entityliving, attacker, kb);
			}
			entityliving.hurtResistantTime += meleeComponent.meleeSpecs.attackDelay;
		}
		
		if (attacker instanceof EntityPlayer && !((EntityPlayer) attacker).capabilities.isCreativeMode)
		{
			EntityPlayer entityplayer = (EntityPlayer) attacker;
			if (itemstack.stackTagCompound == null)
			{
				itemstack.stackTagCompound = new NBTTagCompound();
			}
			int bayonetdamage = itemstack.stackTagCompound.getShort("bayonetDamage") + 1;
			if (bayonetdamage > bayonetDurability)
			{
				/*
				if (deltadamage > 0)
				{
					int m = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemstack);
					int k = 0;
					
					for (int l = 0; j > 0 && l < deltadamage; ++l)
					{
						if (EnchantmentDurability.negateDamage(itemstack, m, entityplayer.getRNG()))
						{
							++k;
						}
					}
					
					deltadamage -= k;
					
					if (deltadamage <= 0)
					{
						deltadamage = 0;
					}
				}
				*/
				entityplayer.renderBrokenItemStack(itemstack);
				int id = Item.getIdFromItem(this);
				if (id != 0)
				{
					BalkonsWeaponMod.modLog.debug("Musket Item (" + toString() + ") ID = " + id);
					entityplayer.addStat(StatList.objectBreakStats[id], 1);
				}
				bayonetdamage = 0;
				itemstack.func_150996_a(BalkonsWeaponMod.musket);
			}
			itemstack.stackTagCompound.setShort("bayonetDamage", (short) bayonetdamage);
		}
		return true;
	}
}
