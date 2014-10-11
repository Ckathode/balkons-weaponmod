package ckathode.weaponmod.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MeleeCompFirerod extends MeleeComponent
{
	public MeleeCompFirerod()
	{
		super(MeleeSpecs.FIREROD, Item.ToolMaterial.WOOD);
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1)
	{
		boolean flag = super.hitEntity(itemstack, entityliving, entityliving1);
		if (flag)
		{
			entityliving.setFire(12 + weapon.getItemRand().nextInt(3)); //TODO add fire instead of set fire
		}
		return flag;
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
		super.onUpdate(itemstack, world, entity, i, flag);
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).inventory.getCurrentItem() == itemstack) if (!entity.isInsideOfMaterial(Material.water))
		{
			float f = 1F;
			float f1 = 27F;
			float particleX = -MathHelper.sin(((entity.rotationYaw + f1) / 180F) * 3.141593F) * MathHelper.cos((entity.rotationPitch / 180F) * 3.141593F) * f;
			float particleY = -MathHelper.sin((entity.rotationPitch / 180F) * 3.141593F) - 0.1F;
			float particleZ = MathHelper.cos(((entity.rotationYaw + f1) / 180F) * 3.141593F) * MathHelper.cos((entity.rotationPitch / 180F) * 3.141593F) * f;
			if (weapon.getItemRand().nextInt(5) == 0)
			{
				world.spawnParticle("flame", entity.posX + particleX, entity.posY + particleY, entity.posZ + particleZ, 0.0D, 0.0D, 0.0D);
			}
			if (weapon.getItemRand().nextInt(5) == 0)
			{
				world.spawnParticle("smoke", entity.posX + particleX, entity.posY + particleY, entity.posZ + particleZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
