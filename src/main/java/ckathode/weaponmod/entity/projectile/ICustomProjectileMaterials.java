package ckathode.weaponmod.entity.projectile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomProjectileMaterials
{
	public int[] getAllMaterialIDs();
	
	public int getMaterialID(ItemStack itemstack);
	
	@SideOnly(Side.CLIENT)
	public float[] getColorFromMaterialID(int id);
}
