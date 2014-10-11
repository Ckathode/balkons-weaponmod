package ckathode.weaponmod.item;

import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMelee extends ItemSword implements IItemWeapon
{
	public final MeleeComponent	meleeComponent;
	
	public ItemMelee(String id, MeleeComponent meleecomponent)
	{
		super((meleecomponent.weaponMaterial == null ? ToolMaterial.WOOD : meleecomponent.weaponMaterial));
		GameRegistry.registerItem(this, id, BalkonsWeaponMod.MOD_ID);
		setUnlocalizedName(id);
		setTextureName("weaponmod:" + id);
		
		meleeComponent = meleecomponent;
		meleecomponent.setItem(this);
		meleecomponent.setThisItemProperties();
	}
	
	@Override
	public float func_150931_i()
	{
		return meleeComponent.getEntityDamageMaterialPart();
	}
	
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
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return meleeComponent.getItemUseAction(itemstack);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return meleeComponent.getMaxItemUseDuration(itemstack);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		return meleeComponent.onLeftClickEntity(itemstack, player, entity);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return meleeComponent.onItemRightClick(itemstack, world, entityplayer);
	}
	
	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count)
	{
		meleeComponent.onUsingTick(itemstack, entityplayer, count);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		meleeComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
		meleeComponent.onUpdate(itemstack, world, entity, i, flag);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers()
	{
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		meleeComponent.addItemAttributeModifiers(multimap);
		return multimap;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldRotateAroundWhenRendering()
	{
		return meleeComponent.shouldRotateAroundWhenRendering();
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
		return null;
	}
}
