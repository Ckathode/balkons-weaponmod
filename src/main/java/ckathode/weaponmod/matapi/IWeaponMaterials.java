package ckathode.weaponmod.matapi;

import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IWeaponMaterials
{
	public Item.ToolMaterial[] getWMCustomMaterials();
	
	/**
	 * @param toolmaterial
	 *            The tool material the custom knockback is requested for.
	 * @return A custom knockback multiplier, use 1.0f for default knockback.
	 */
	public float getWMKnockbackMultiplier(Item.ToolMaterial toolmaterial);
	
	/**
	 * @param toolmaterial
	 *            The tool material the color is requested for.
	 * @return A float array of length 4, containing the RGBA components of the color to use while rendering a projectile.
	 */
	@SideOnly(Side.CLIENT)
	public float[] getWMProjectileColor(Item.ToolMaterial toolmaterial);
}
