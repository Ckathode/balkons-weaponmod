package ckathode.weaponmod.entity.projectile.dispense;

import java.util.Random;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;

public class DispenseBlunderShot extends BehaviorDefaultDispenseItem
{
	private Random	rand;
	
	public DispenseBlunderShot()
	{
		rand = new Random();
	}
	
	@Override
	public ItemStack dispenseStack(IBlockSource blocksource, ItemStack itemstack)
	{
		EnumFacing face = EnumFacing.getFront(blocksource.getBlockMetadata());
		
		IPosition pos = BlockDispenser.func_149939_a(blocksource);
		EntityBlunderShot.fireFromDispenser(blocksource.getWorld(), pos.getX() + face.getFrontOffsetX(), pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), face.getFrontOffsetX(), face.getFrontOffsetY(), face.getFrontOffsetZ());
		itemstack.splitStack(1);
		return itemstack;
	}
	
	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.explode", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.6F));
	}
	
	@Override
	protected void spawnDispenseParticles(IBlockSource blocksource, EnumFacing face)
	{
		super.spawnDispenseParticles(blocksource, face);
		IPosition pos = BlockDispenser.func_149939_a(blocksource);
		blocksource.getWorld().spawnParticle("flame", pos.getX() + face.getFrontOffsetX(), pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
	}
}
