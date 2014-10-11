package ckathode.weaponmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDynamite extends WMItem
{
	public ItemDynamite(String id)
	{
		super(id);
		maxStackSize = 64;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return 0;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (entityplayer.inventory.consumeInventoryItem(this))
		{
			world.playSoundAtEntity(entityplayer, "game.tnt.primed", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote)
			{
				world.spawnEntityInWorld(new EntityDynamite(world, entityplayer, 40 + itemRand.nextInt(10)));
			}
		}
		return itemstack;
	}
	
	private void spawnSmoke(EntityPlayer entityplayer)
	{
		float particleX;
		float particleY;
		float particleZ;
		
		particleX = -MathHelper.sin(((entityplayer.rotationYaw + 23) / 180F) * 3.141593F) * MathHelper.cos((entityplayer.rotationPitch / 180F) * 3.141593F);
		particleY = -MathHelper.sin((entityplayer.rotationPitch / 180F) * 3.141593F);
		particleZ = MathHelper.cos(((entityplayer.rotationYaw + 23) / 180F) * 3.141593F) * MathHelper.cos((entityplayer.rotationPitch / 180F) * 3.141593F);
		
		entityplayer.worldObj.spawnParticle("smoke", entityplayer.posX + particleX, entityplayer.posY + particleY, entityplayer.posZ + particleZ, 0.0D, 0.0D, 0.0D);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
}
