package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;

public class DispenseBlowgunDart extends DispenseWeaponProjectile
{
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition pos, ItemStack itemstack)
	{
		EntityBlowgunDart dart = (EntityBlowgunDart) getProjectileEntity(world, pos);
		dart.setDartEffectType(itemstack.getItemDamage());
		return dart;
	}
	
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition pos)
	{
		return new EntityBlowgunDart(world, pos.getX(), pos.getY(), pos.getZ());
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
