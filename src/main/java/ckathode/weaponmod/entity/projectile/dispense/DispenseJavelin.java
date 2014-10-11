package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityJavelin;

public class DispenseJavelin extends DispenseWeaponProjectile
{
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition pos)
	{
		return new EntityJavelin(world, pos.getX(), pos.getY(), pos.getZ());
	}
	
	@Override
	public float getDeviation()
	{
		return 4.0F;
	}
	
	@Override
	public float getVelocity()
	{
		return 1.1F;
	}

	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.bow", 1.0F, 1.2F);
	}
}
