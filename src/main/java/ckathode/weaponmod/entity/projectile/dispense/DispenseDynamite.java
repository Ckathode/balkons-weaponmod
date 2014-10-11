package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityDynamite;

public class DispenseDynamite extends DispenseWeaponProjectile
{
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition pos)
	{
		return new EntityDynamite(world, pos.getX(), pos.getY(), pos.getZ());
	}
	
	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.fuse", 1.0F, 1.2F);
	}
}
