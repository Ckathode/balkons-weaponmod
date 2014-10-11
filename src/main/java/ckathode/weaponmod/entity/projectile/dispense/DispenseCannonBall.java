package ckathode.weaponmod.entity.projectile.dispense;

import java.util.Random;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;

public class DispenseCannonBall extends BehaviorDefaultDispenseItem
{
	private Random	rand;
	private boolean	normalDispense;
	
	public DispenseCannonBall()
	{
		super();
		rand = new Random();
		normalDispense = false;
	}
	
	@Override
	public ItemStack dispenseStack(IBlockSource blocksource, ItemStack itemstack)
	{
		boolean canfire = false;
		normalDispense = false;
		TileEntity tileentity = blocksource.getWorld().getTileEntity(MathHelper.floor_double(blocksource.getXInt()), MathHelper.floor_double(blocksource.getYInt()), MathHelper.floor_double(blocksource.getZInt()));
		if (tileentity instanceof TileEntityDispenser)
		{
			TileEntityDispenser dispenser = ((TileEntityDispenser) tileentity);
			Item itemtocheck = null;
			if (itemstack.getItem() == Items.gunpowder)
			{
				itemtocheck = BalkonsWeaponMod.cannonBall;
			} else if (itemstack.getItem() == BalkonsWeaponMod.cannonBall)
			{
				itemtocheck = Items.gunpowder;
			}
			
			for (int i = 0; i < dispenser.getSizeInventory(); i++)
			{
				ItemStack itemstack1 = dispenser.getStackInSlot(i);
				if (itemstack1 != null && itemstack1.getItem() == itemtocheck)
				{
					dispenser.decrStackSize(i, 1);
					canfire = true;
					break;
				}
			}
		}
		
		if (!canfire)
		{
			normalDispense = true;
			return super.dispenseStack(blocksource, itemstack);
		}
		
		EnumFacing face = BlockDispenser.func_149937_b(blocksource.getBlockMetadata());
		double xvel = face.getFrontOffsetX() * 1.5D;
		double yvel = face.getFrontOffsetY() * 1.5D;
		double zvel = face.getFrontOffsetZ() * 1.5D;
		IPosition pos = BlockDispenser.func_149939_a(blocksource);
		
		EntityCannonBall entitycannonball = new EntityCannonBall(blocksource.getWorld(), pos.getX() + xvel, pos.getY() + yvel, pos.getZ() + zvel);
		entitycannonball.setThrowableHeading(xvel, yvel + 0.15D, zvel, 2.0F, 2.0F);
		blocksource.getWorld().spawnEntityInWorld(entitycannonball);
		itemstack.splitStack(1);
		return itemstack;
	}
	
	@Override
	protected void playDispenseSound(IBlockSource blocksource)
	{
		if (normalDispense)
		{
			super.playDispenseSound(blocksource);
			return;
		}
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.explode", 8.0F, 1.0F / (rand.nextFloat() * 0.8F + 0.9F));
		blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "ambient.weather.thunder", 8.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.6F));
	}
	
	@Override
	protected void spawnDispenseParticles(IBlockSource blocksource, EnumFacing face)
	{
		super.spawnDispenseParticles(blocksource, face);
		if (!normalDispense)
		{
			IPosition pos = BlockDispenser.func_149939_a(blocksource);
			blocksource.getWorld().spawnParticle("flame", pos.getX() + face.getFrontOffsetX(), pos.getY(), pos.getZ() + face.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
		}
	}
}
