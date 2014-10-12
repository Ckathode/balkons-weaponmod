package ckathode.weaponmod.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponModAttributes;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MeleeComponent extends AbstractWeaponComponent
{
	public final MeleeSpecs			meleeSpecs;
	public final Item.ToolMaterial	weaponMaterial;
	
	public MeleeComponent(MeleeSpecs meleespecs, Item.ToolMaterial toolmaterial)
	{
		meleeSpecs = meleespecs;
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
			item.setMaxDamage(meleeSpecs.durabilityBase);
		} else
		{
			item.setMaxDamage((int) (meleeSpecs.durabilityBase + weaponMaterial.getMaxUses() * meleeSpecs.damageMult));
		}
		item.setMaxStackSize(meleeSpecs.stackSize);
	}
	
	@Override
	public float getEntityDamageMaterialPart()
	{
		if (weaponMaterial == null)
		{
			return 0F;
		}
		return weaponMaterial.getDamageVsEntity() * meleeSpecs.damageMult;
	}
	
	@Override
	public float getEntityDamage()
	{
		return meleeSpecs.damageBase + getEntityDamageMaterialPart();
	}
	
	@Override
	public float getBlockDamage(ItemStack itemstack, Block block)
	{
		if (canHarvestBlock(block))
		{
			return meleeSpecs.blockDamage * 10F;
		}
		Material material = block.getMaterial();
		return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : meleeSpecs.blockDamage;
	}
	
	@Override
	public boolean canHarvestBlock(Block block)
	{
		return block == Blocks.web;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int j, int k, int l, EntityLivingBase entityliving)
	{
		if ((meleeSpecs.blockDamage > 1F || canHarvestBlock(block)) && block.getBlockHardness(world, j, k, l) != 0F)
		{
			itemstack.damageItem(meleeSpecs.dmgFromBlock, entityliving);
		}
		return true;
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime)
		{
			float kb = getKnockBack(itemstack, entityliving, attacker);
			//if (entityliving.onGround)
			{
				PhysHelper.knockBack(entityliving, attacker, kb);
			}
			entityliving.hurtResistantTime += getAttackDelay(itemstack, entityliving, attacker);
		}
		itemstack.damageItem(meleeSpecs.dmgFromEntity, attacker);
		return true;
	}
	
	@Override
	public int getAttackDelay(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return meleeSpecs.attackDelay;
	}
	
	@Override
	public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return meleeSpecs.getKnockBack(weaponMaterial);
	}
	
	@Override
	public int getItemEnchantability()
	{
		return weaponMaterial == null ? 1 : weaponMaterial.getEnchantability();
	}
	
	@Override
	public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap)
	{
		float dmg = getEntityDamage();
		if (dmg > 0F || meleeSpecs.damageMult > 0F)
		{
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(weapon.getUUID(), "Weapon modifier", dmg, 0));
		}
		multimap.put(WeaponModAttributes.WEAPON_KNOCKBACK.getAttributeUnlocalizedName(), new AttributeModifier(weapon.getUUID(), "Weapon knockback modifier", meleeSpecs.getKnockBack(weaponMaterial) - 0.4F, 0));
		multimap.put(WeaponModAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(weapon.getUUID(), "Weapon attack speed modifier", -meleeSpecs.attackDelay, 0));
		if (this instanceof IExtendedReachItem)
		{
			try
			{
				multimap.put(WeaponModAttributes.WEAPON_REACH.getAttributeUnlocalizedName(), new AttributeModifier(weapon.getUUID(), "Weapon reach modifier", ((IExtendedReachItem) this).getExtendedReach(null, null, null) - 3F, 0));
			} catch (NullPointerException e)
			{}
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if (entity instanceof EntityLivingBase)
		{
			PhysHelper.prepareKnockbackOnEntity(player, (EntityLivingBase) entity);
		}
		return false;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.block;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 72000;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
	
	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count)
	{
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldRotateAroundWhenRendering()
	{
		return false;
	}
	
	public static enum MeleeSpecs
	{
		//NAME db, dm, edb, edm, bd, kb, dfe, dfb, mss, ad
		SPEAR(0, 1F, 3, 1F, 1F, 0.2F, 1, 2, 1, 0),
		HALBERD(0, 1F, 4, 1F, 1.5F, 0.6F, 1, 2, 1, 8),
		BATTLEAXE(0, 1F, 3, 1F, 1.5F, 0.5F, 1, 2, 1, 5),
		WARHAMMER(0, 1F, 4, 1F, 1F, 0.7F, 1, 2, 1, 5),
		KNIFE(0, 0.5F, 3, 1F, 1.5F, 0.2F, 1, 2, 1, 0),
		KATANA(0, 1F, 1, 1F, 1F, 0F, 1, 2, 1, -6),
		FIREROD(1, 0F, 1, 0F, 1F, 0.4F, 2, 0, 1, 0),
		BOOMERANG(0, 0.5F, 2, 1F, 1F, 0.4F, 1, 1, 1, 0),
		NONE(0, 0F, 1, 0F, 1F, 0.4F, 0, 0, 1, 0);
		
		private MeleeSpecs(int durbase, float durmult, float dmgbase, float dmgmult, float blockdmg, float knockback, int dmgfromentity, int dmgfromblock, int stacksize, int attackdelay)
		{
			durabilityBase = durbase;
			durabilityMult = durmult;
			
			damageBase = dmgbase;
			damageMult = dmgmult;
			blockDamage = blockdmg;
			this.knockback = knockback;
			
			dmgFromEntity = dmgfromentity;
			dmgFromBlock = dmgfromblock;
			
			stackSize = stacksize;
			attackDelay = attackdelay;
		}
		
		public float getKnockBack(ToolMaterial material)
		{
			return material == ToolMaterial.GOLD ? knockback * 1.5F : knockback;
		}
		
		public final int	durabilityBase;
		public final float	durabilityMult;
		
		public final float	damageBase, damageMult, blockDamage, knockback;
		
		public final int	dmgFromEntity, dmgFromBlock;
		
		public final int	stackSize, attackDelay;
	}
}
