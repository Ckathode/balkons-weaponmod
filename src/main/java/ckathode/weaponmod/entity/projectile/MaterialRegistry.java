package ckathode.weaponmod.entity.projectile;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class MaterialRegistry
{
	private static final float[]							NO_MATERIAL_COLOR	= { 1F, 1F, 1F };
	private static Map<Integer, ICustomProjectileMaterials>	customMaterials;
	
	public static void registerCustomProjectileMaterial(ICustomProjectileMaterials customprojectilematerial)
	{
		if (customMaterials == null)
		{
			customMaterials = new HashMap<Integer, ICustomProjectileMaterials>(4);
		}
		
		int[] ai = customprojectilematerial.getAllMaterialIDs();
		for (int i : ai)
		{
			customMaterials.put(Integer.valueOf(i), customprojectilematerial);
		}
	}
	
	public static int getMaterialID(ItemStack itemstack)
	{
		if (customMaterials != null)
		{
			int i;
			for (ICustomProjectileMaterials mat : customMaterials.values())
			{
				i = mat.getMaterialID(itemstack);
				if (i > 4) return i;
			}
		}
		return -1;
	}
	
	public static float[] getColorFromMaterialID(int id)
	{
		if (customMaterials != null)
		{
			ICustomProjectileMaterials mat = customMaterials.get(Integer.valueOf(id));
			if (mat != null)
			{
				return mat.getColorFromMaterialID(id);
			}
		}
		return NO_MATERIAL_COLOR;
	}
}
