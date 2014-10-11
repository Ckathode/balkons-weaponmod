package ckathode.weaponmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJavelin extends WMItem
{
	public ItemJavelin(String id)
	{
		super(id);
		maxStackSize = 16;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return 0;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		if (!entityplayer.inventory.hasItem(this)) return;
		
		int j = getMaxItemUseDuration(itemstack) - i;
		float f = j / 20F;
		f = (f * f + f * 2.0F) / 3F;
		if (f < 0.1F) return;
		if (f > 1.0F)
		{
			f = 1.0F;
		}
		
		boolean crit = false;
		if (!entityplayer.onGround && !entityplayer.isInWater())
		{
			crit = true;
		}
		
		if (entityplayer.capabilities.isCreativeMode || entityplayer.inventory.consumeInventoryItem(this))
		{
			world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote)
			{
				EntityJavelin entityjavelin = new EntityJavelin(world, entityplayer, f * (1.0F + (crit ? 0.5F : 0F)));
				entityjavelin.setIsCritical(crit);
				world.spawnEntityInWorld(entityjavelin);
			}
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 0x11940;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.bow;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (entityplayer.inventory.hasItem(this))
		{
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
}
