package ckathode.weaponmod.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AbstractWeaponComponent
{
	public Item	item;
	IItemWeapon	weapon;
	
	public AbstractWeaponComponent()
	{
		item = null;
		weapon = null;
	}
	
	final void setItem(IItemWeapon itemweapon)
	{
		item = (Item) itemweapon;
		weapon = itemweapon;
		onSetItem();
	}
	
	protected abstract void onSetItem();
	
	public abstract void setThisItemProperties();
	
	public abstract float getEntityDamageMaterialPart();
	
	public abstract float getEntityDamage();
	
	public abstract float getBlockDamage(ItemStack itemstack, Block block);
	
	public abstract boolean canHarvestBlock(Block block);
	
	public abstract boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int j, int k, int l, EntityLivingBase entityliving);
	
	public abstract boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker);
	
	public abstract int getAttackDelay(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker);
	
	public abstract float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker);
	
	public abstract int getItemEnchantability();
	
	public abstract void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap);
	
	public abstract EnumAction getItemUseAction(ItemStack itemstack);
	
	public abstract int getMaxItemUseDuration(ItemStack itemstack);
	
	public abstract boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity);
	
	public abstract ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer);
	
	public abstract void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count);
	
	public abstract void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i);
	
	public abstract void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag);
	
	@SideOnly(Side.CLIENT)
	public abstract boolean shouldRotateAroundWhenRendering();
}
