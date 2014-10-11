package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;

public class DispenseCrossbowBolt extends DispenseWeaponProjectile
{
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition pos)
	{
		return new EntityCrossbowBolt(world, pos.getX(), pos.getY(), pos.getZ());
	}
	
	@Override
	public float getVelocity()
	{
		return 3.0F;
	}
	
	@Override
	public float getDeviation()
	{
		return 2.0F;
	}
	
	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.bow", 1.0F, 1.2F);
	}
}
