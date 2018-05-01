package ckathode.weaponmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDynamite extends WMItem
{
	int explodefuse;
	public ItemDynamite(String id)
	{
		super(id);
		maxStackSize = 64;
	}

	@Override
	public int getItemEnchantability()
	{
		return 0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{	
		if (entityplayer.isSneaking())
		{
			if (entityplayer.inventory.hasItem(this))
			{
				entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
			}
		}
		else if (entityplayer.inventory.consumeInventoryItem(this))
		{
				
				world.playSoundAtEntity(entityplayer, "game.tnt.primed", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
					if (!world.isRemote)
					{
						world.spawnEntityInWorld(new EntityDynamite(world, entityplayer, 40 + itemRand.nextInt(10), 0.7F));
					}
				
			
		}
		return itemstack;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		if (!entityplayer.inventory.hasItem(this)) return;
		
		int j = getMaxItemUseDuration(itemstack) - i;
		float f = j / 20F;
		f = (f * f + f * 2.0F) / 3F;
		if (f > 1.0F)
		{
			f = 1.0F;
		}
		
		if (entityplayer.capabilities.isCreativeMode || entityplayer.inventory.consumeInventoryItem(this))
		{
			world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote)
			{
				EntityDynamite entitydynamite = new EntityDynamite(world, entityplayer, 40 + itemRand.nextInt(10), 0.7F + f * 0.5F);
				world.spawnEntityInWorld(entitydynamite);
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
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
}
