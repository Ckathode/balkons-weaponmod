package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;

public class DispenseMusketBullet extends DispenseWeaponProjectile
{
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition pos)
	{
		return new EntityMusketBullet(world, pos.getX(), pos.getY(), pos.getZ());
	}
	
	@Override
	public double getYVel()
	{
		return 0D;
	}
	
	@Override
	public float getDeviation()
	{
		return 3F;
	}
	
	@Override
	public float getVelocity()
	{
		return 5F;
	}
	
	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.explode", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.7F));
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "ambient.weather.thunder", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.4F));
	}
	
	@Override
	protected void spawnDispenseParticles(IBlockSource blocksource, EnumFacing face)
	{
		super.spawnDispenseParticles(blocksource, face);
		IPosition pos = BlockDispenser.func_149939_a(blocksource);
		blocksource.getWorld().spawnParticle("flame", pos.getX() + face.getFrontOffsetX(), pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), 0.0D, 0.2D, 0.0D);
	}
}
