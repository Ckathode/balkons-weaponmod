package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityRocketShell;

public class DispenseRocketShell extends DispenseWeaponProjectile
{
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition pos)
	{
		return new EntityRocketShell(world, pos.getX(), pos.getY(), pos.getZ());
	}
	
	@Override
	public double getYVel()
	{
		return 0D;
	}
	
	@Override
	public float getDeviation()
	{
		return 0F;
	}
	
	@Override
	public float getVelocity()
	{
		return 3F;
	}
	
	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "mob.wither.shoot", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.4F));
	}
	
}
