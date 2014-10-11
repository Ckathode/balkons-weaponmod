package ckathode.weaponmod.entity.projectile.dispense;

import java.util.Random;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class DispenseWeaponProjectile extends BehaviorProjectileDispense
{
	protected Random	rand;
	
	public DispenseWeaponProjectile()
	{
		rand = new Random();
	}
	
	@Override
	public ItemStack dispenseStack(IBlockSource blocksource, ItemStack itemstack)
	{
		World world = blocksource.getWorld();
		IPosition pos = BlockDispenser.func_149939_a(blocksource);
		EnumFacing face = BlockDispenser.func_149937_b(blocksource.getBlockMetadata());
		IProjectile projectile = getProjectileEntity(world, pos, itemstack);
		projectile.setThrowableHeading(face.getFrontOffsetX(), face.getFrontOffsetY() + getYVel(), face.getFrontOffsetZ(), getVelocity(), getDeviation());
		world.spawnEntityInWorld((Entity) projectile);
		itemstack.splitStack(1);
		return itemstack;
	}
	
	protected IProjectile getProjectileEntity(World world, IPosition pos, ItemStack itemstack)
	{
		return getProjectileEntity(world, pos);
	}
	
	public double getYVel()
	{
		return 0.1D;
	}
	
	public float getVelocity()
	{
		return func_82500_b();
	}
	
	public float getDeviation()
	{
		return func_82498_a();
	}
	
	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		super.playDispenseSound(blocksource);
	}
	
	@Override
	protected void spawnDispenseParticles(IBlockSource blocksource, EnumFacing facing)
	{
		super.spawnDispenseParticles(blocksource, facing);
	}
}
