package ckathode.weaponmod.matapi;

import net.minecraft.item.Item;

public class WeaponMaterial
{
	public final Item.ToolMaterial	toolMaterial;
	public final float				knockbackMult;
	public final int				projectileColori;
	private float[]					projectileColorf;
	
	WeaponMaterial(Item.ToolMaterial toolmaterial, int color, float knockback)
	{
		toolMaterial = toolmaterial;
		projectileColori = color;
		this.knockbackMult = knockback;
		
		projectileColorf = null;
	}
	
	public float[] getProjectileColor()
	{
		if (projectileColorf == null)
		{
			projectileColorf = new float[4];
			projectileColorf[0] = ((projectileColori >> 24) & 0xFF) / 255f;
			projectileColorf[1] = ((projectileColori >> 16) & 0xFF) / 255f;
			projectileColorf[2] = ((projectileColori >> 8) & 0xFF) / 255f;
			projectileColorf[3] = (projectileColori & 0xFF) / 255f;
		}
		return projectileColorf;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(toolMaterial.name());
		sb.append(" { projectileColor: ").append(Integer.toHexString(projectileColori));
		sb.append(", knockbackMult: ").append(knockbackMult).append(" }");
		return sb.toString();
	}
}
