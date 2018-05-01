package ckathode.weaponmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.entity.projectile.EntityRocketShell;

public class RangedCompRocket extends RangedComponent
{
	
	public RangedCompRocket()
	{
		super(RangedSpecs.ROCKET, null);
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
		int j = getMaxItemUseDuration(itemstack) - i;
		float f = j / 20F;
		f = (f * f + f * 2F) / 3F;
		if (f > 1.0F)
		{
			f = 1.0F;
		}
		f += 0.02F;

		if (!world.isRemote)
		{
			EntityRocketShell entityrocketshell = new EntityRocketShell(world, entityplayer, 3.0F, 0.5F / f);
			applyProjectileEnchantments(entityrocketshell, itemstack);
			world.spawnEntityInWorld(entityrocketshell);
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
	public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world)
	{
	}

	@Override
	public void effectShoot(World world, double x, double y, double z, float yaw, float pitch)
	{
		world.playSoundEffect(x, y, z, "mob.wither.shoot", 3F, 1F / (weapon.getItemRand().nextFloat() * 0.4F + 0.4F));

		float particleX = -MathHelper.sin(((yaw + 23) / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
		float particleY = -MathHelper.sin((pitch / 180F) * 3.141593F) - 0.1F;
		float particleZ = MathHelper.cos(((yaw + 23) / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);

		for (int i = 0; i < 3; i++)
		{
			world.spawnParticle("smoke", x + particleX, y + particleY, z + particleZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public float getMaxZoom(EntityPlayer entityplayer)
	{
		return 0.15f;
	}

}
