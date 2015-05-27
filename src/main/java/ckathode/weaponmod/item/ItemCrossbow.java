package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrossbow extends ItemShooter
{
	public ModelResourceLocation	iconIndexLoaded;
	
	public ItemCrossbow(String id, RangedComponent rangedcomponent, MeleeComponent meleecomponent)
	{
		super(id, rangedcomponent, meleecomponent);
        ModelBakery.addVariantName(this, BalkonsWeaponMod.MOD_ID + ":" + id);
        ModelBakery.addVariantName(this, BalkonsWeaponMod.MOD_ID + ":" + id + "-loaded");
	}
}
