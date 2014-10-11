package ckathode.weaponmod.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ckathode.weaponmod.BalkonsWeaponMod;
import cpw.mods.fml.common.registry.GameRegistry;

public class WMItem extends Item
{
	public WMItem(String id)
	{
		GameRegistry.registerItem(this, id, BalkonsWeaponMod.MOD_ID);
		setUnlocalizedName(id);
		setTextureName("weaponmod:" + id);
		setCreativeTab(CreativeTabs.tabCombat);
	}
	
}
