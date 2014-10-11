package ckathode.weaponmod;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class WeaponModAttributes extends SharedMonsterAttributes
{
	public static final BaseAttribute	IGNORE_ARMOUR_DAMAGE	= new RangedAttribute("weaponmod.ignoreArmour", 0D, 0D, Double.MAX_VALUE);
	public static final BaseAttribute	WEAPON_KNOCKBACK		= new RangedAttribute("weaponmod.knockback", 0.4D, 0D, Double.MAX_VALUE);
	public static final BaseAttribute	ATTACK_SPEED			= new RangedAttribute("weaponmod.attackSpeed", 0D, -Double.MAX_VALUE, Double.MAX_VALUE);
	public static final BaseAttribute	RELOAD_TIME				= new RangedAttribute("weaponmod.reloadTime", 0D, 0D, Double.MAX_VALUE);
	public static final BaseAttribute	WEAPON_REACH			= new RangedAttribute("weaponmod.reach", 0D, 0D, Double.MAX_VALUE);
}
