package ckathode.weaponmod.entity.projectile;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Ckathode
 * @deprecated Use IWeaponMaterials and the WeaponMaterialAPI instead.
 */
@Deprecated
public interface ICustomProjectileMaterials
{
	public int[] getAllMaterialIDs();
	
	public int getMaterialID(ItemStack itemstack);
	
	@SideOnly(Side.CLIENT)
	public float[] getColorFromMaterialID(int id);
}
