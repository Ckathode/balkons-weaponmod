package ckathode.weaponmod.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;

public class MeleeCompBoomerang extends MeleeComponent
{
	public MeleeCompBoomerang(ToolMaterial toolmaterial)
	{
		super(MeleeSpecs.BOOMERANG, toolmaterial);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		if (!entityplayer.inventory.hasItem(item)) return;
		
		int j = getMaxItemUseDuration(itemstack) - i;
		float f = j / 20F;
		f = (f * f + f * 2.0F) / 3F;
		if (f < 0.1F) return;
		
		boolean crit = false;
		if (f > 1.5F)
		{
			f = 1.5F;
			crit = true;
		}
		f *= 1.5F;
		
		world.playSoundAtEntity(entityplayer, "random.bow", 0.6F, 1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 1.0F));
		if (!world.isRemote)
		{
			EntityBoomerang entityboomerang = new EntityBoomerang(world, entityplayer, itemstack, f);
			entityboomerang.setIsCritical(crit);
			entityboomerang.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemstack));
			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemstack) > 0)
			{
				entityboomerang.setFire(100);
			}
			world.spawnEntityInWorld(entityboomerang);
		}
		
		if (!entityplayer.capabilities.isCreativeMode)
		{
			ItemStack itemstack2 = itemstack.copy();
			if (--itemstack2.stackSize == 0)
			{
				itemstack2 = null;
			}
			entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack2;
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.block;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (entityplayer.inventory.hasItem(item))
		{
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}
}
