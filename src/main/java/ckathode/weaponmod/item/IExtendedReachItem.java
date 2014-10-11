package ckathode.weaponmod.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IExtendedReachItem
{
	//Approx. 3.0 By default.
	public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack);
}
