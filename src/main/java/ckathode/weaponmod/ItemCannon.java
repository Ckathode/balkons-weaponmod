package ckathode.weaponmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ckathode.weaponmod.entity.EntityCannon;

public class ItemCannon extends WMItem
{
	public ItemCannon(String id)
	{
		super(id);
		maxStackSize = 1;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return 10;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		float f = 1.0F;
		float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
		float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
		double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * f;
		double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * f + 1.6200000000000001D) - entityplayer.yOffset;
		double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * f;
		Vec3 vec3d = Vec3.createVectorHelper(d, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
		float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-f1 * 0.01745329F);
		float f6 = MathHelper.sin(-f1 * 0.01745329F);
		float f7 = f4 * f5;
		float f8 = f6;
		float f9 = f3 * f5;
		double d3 = 5D;
		Vec3 vec3d1 = vec3d.addVector(f7 * d3, f8 * d3, f9 * d3);
		MovingObjectPosition movingobjectposition = world.rayTraceBlocks(vec3d, vec3d1, true);
		if (movingobjectposition != null)
		{
			if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK)
			{
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;
				if (!world.isRemote)
				{
					if (world.getBlock(i, j, k) == Blocks.snow)
					{
						j--;
					}
					world.spawnEntityInWorld(new EntityCannon(world, i + 0.5F, j + 1.0F, k + 0.5F));
				}
				if (!entityplayer.capabilities.isCreativeMode || !world.isRemote)
				{
					itemstack.stackSize--;
				}
			}
		}
		return itemstack;
	}
}
