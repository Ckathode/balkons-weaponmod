package ckathode.weaponmod.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

public class MeleeCompNone extends MeleeComponent
{
	public MeleeCompNone()
	{
		super(MeleeSpecs.NONE, null);
	}
	
	@Override
	public float getEntityDamageMaterialPart()
	{
		return 0F;
	}
	
	@Override
	public float getEntityDamage()
	{
		return 1F;
	}
	
	@Override
	public float getBlockDamage(ItemStack itemstack, Block block)
	{
		return 1F;
	}
	
	@Override
	public boolean canHarvestBlock(Block block)
	{
		return false;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int j, int k, int l, EntityLivingBase entityliving)
	{
		return true;
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return true;
	}
	
	@Override
	public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return 0F;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return 1;
	}
	
	@Override
	public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap)
	{
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		return false;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.none;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return itemstack;
	}
}
