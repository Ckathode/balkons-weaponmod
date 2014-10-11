package ckathode.weaponmod;

import net.minecraft.util.DamageSource;

public class DamageSourceAxe extends DamageSource
{
	public DamageSourceAxe()
	{
		super("battleaxe");
		setDamageBypassesArmor();
	}
}