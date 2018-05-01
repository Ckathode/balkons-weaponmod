package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityScrew;

public class DispenseScrew extends DispenseWeaponProjectile
{
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition pos)
	{
		return new EntityScrew(world, pos.getX(), pos.getY(), pos.getZ());
	}
	
	@Override
	public double getYVel()
	{
		return 0D;
	}
	
	@Override
	public float getDeviation()
	{
		return 2F;
	}
	
	@Override
	public float getVelocity()
	{
		return 5F;
	}
	
	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.2F + 0.5F));
	}
	
}
