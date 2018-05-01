package ckathode.weaponmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.entity.projectile.EntityScrew;

public class RangedCompNailgun extends RangedComponent
{

	public RangedCompNailgun()
	{
		super(RangedSpecs.NAILGUN, null);
	}

	@Override
	public void effectReloadDone(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		entityplayer.swingItem();
		world.playSoundAtEntity(entityplayer, "random.click", 1.0F, 1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.8F));
	}

	@Override
	public void fire(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		
		if (!world.isRemote)
		{
			EntityScrew entityscrew = new EntityScrew(world, entityplayer, 5.0F, 2F);
			applyProjectileEnchantments(entityscrew, itemstack);
			world.spawnEntityInWorld(entityscrew);
		}

		int damage = 1;
		if (itemstack.getItemDamage() + damage <= itemstack.getMaxDamage())
		{
			setReloadState(itemstack, ReloadHelper.STATE_NONE);
		}
		
		itemstack.damageItem(damage, entityplayer);
		postShootingEffects(itemstack, entityplayer, world);
	}
	
	@Override
	public void effectShoot(World world, double x, double y, double z, float yaw, float pitch)
	{
		world.playSoundEffect(x, y, z, "random.bow", 1.0F, 1.0F / (weapon.getItemRand().nextFloat() * 0.2F + 0.5F));
	}

	@Override
	public float getMaxZoom(EntityPlayer entityplayer)
	{
		return 0f;
	}

	@Override
	public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getReloadType(ItemStack itemstack)
	{
		return SEMI_AUTO;
	}
}
