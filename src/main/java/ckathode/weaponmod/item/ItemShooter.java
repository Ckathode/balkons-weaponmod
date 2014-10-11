package ckathode.weaponmod.item;

import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		setTextureName("weaponmod:" + id);
		
		rangedComponent = rangedcomponent;
		meleeComponent = meleecomponent;
		
		rangedcomponent.setItem(this);
		meleecomponent.setItem(this);
		
		rangedcomponent.setThisItemProperties();
	}
	
	// MELEE PART //
	@Override
	public float func_150893_a(ItemStack itemstack, Block block)
	{
		return meleeComponent.getBlockDamage(itemstack, block);
	}
	
	@Override
	public boolean func_150897_b(Block block)
	{
		return meleeComponent.canHarvestBlock(block);
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		return meleeComponent.hitEntity(itemstack, entityliving, attacker);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int j, int k, int l, EntityLivingBase entityliving)
	{
		return meleeComponent.onBlockDestroyed(itemstack, world, block, j, k, l, entityliving);
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
		return field_111210_e;
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
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon(getIconString());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
}
