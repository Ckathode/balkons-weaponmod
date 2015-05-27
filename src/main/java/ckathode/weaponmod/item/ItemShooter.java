package ckathode.weaponmod.item;

import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShooter extends ItemBow implements IItemWeapon
{
	protected static final int		MAX_DELAY	= 72000;
	
	public final RangedComponent	rangedComponent;
	public final MeleeComponent		meleeComponent;
	
	public ItemShooter(String id, RangedComponent rangedcomponent, MeleeComponent meleecomponent)
	{
		super();
		GameRegistry.registerItem(this, id, BalkonsWeaponMod.MOD_ID);
		setUnlocalizedName(id);
		
		rangedComponent = rangedcomponent;
		meleeComponent = meleecomponent;
		
		rangedcomponent.setItem(this);
		meleecomponent.setItem(this);
		
		rangedcomponent.setThisItemProperties();
	}
	
	// MELEE PART //
	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block)
	{
		return meleeComponent.getBlockDamage(itemstack, block);
	}
	
	@Override
	public boolean canHarvestBlock(Block block)
	{
		return meleeComponent.canHarvestBlock(block);
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return meleeComponent.hitEntity(itemstack, entityliving, attacker);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, BlockPos pos, EntityLivingBase entityliving)
	{
		return meleeComponent.onBlockDestroyed(itemstack, world, block, pos, entityliving);
	}
	
	@Override
	public int getItemEnchantability()
	{
		return meleeComponent.getItemEnchantability();
	}
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers()
	{
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		meleeComponent.addItemAttributeModifiers(multimap);
		rangedComponent.addItemAttributeModifiers(multimap);
		return multimap;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		return meleeComponent.onLeftClickEntity(itemstack, player, entity) && rangedComponent.onLeftClickEntity(itemstack, player, entity);
	}
	
	///////
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return rangedComponent.getItemUseAction(itemstack);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return rangedComponent.getMaxItemUseDuration(itemstack);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return rangedComponent.onItemRightClick(itemstack, world, entityplayer);
	}
	
	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count)
	{
		rangedComponent.onUsingTick(itemstack, entityplayer, count);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		rangedComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
		meleeComponent.onUpdate(itemstack, world, entity, i, flag);
		rangedComponent.onUpdate(itemstack, world, entity, i, flag);
	}
	
	@Override
	public boolean shouldRotateAroundWhenRendering()
	{
		return rangedComponent.shouldRotateAroundWhenRendering();
	}
	
	@Override
	public final UUID getUUID()
	{
		return itemModifierUUID;
	}
	
	@Override
	public final Random getItemRand()
	{
		return itemRand;
	}
	
	@Override
	public MeleeComponent getMeleeComponent()
	{
		return meleeComponent;
	}
	
	@Override
	public RangedComponent getRangedComponent()
	{
		return rangedComponent;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
}
