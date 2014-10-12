package ckathode.weaponmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;

public class RangedCompMusket extends RangedComponent
{
	protected ItemMusket	musket;

	public RangedCompMusket()
	{
		super(RangedSpecs.MUSKET);
	}

	@Override
	protected void onSetItem()
	{
		super.onSetItem();
		if (item instanceof ItemMusket)
		{
			musket = (ItemMusket) item;
		}
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
			EntityMusketBullet entitymusketbullet = new EntityMusketBullet(world, entityplayer, 1F / f);
			applyProjectileEnchantments(entitymusketbullet, itemstack);
			world.spawnEntityInWorld(entitymusketbullet);
		}

		int deltadamage = 1;

		boolean flag = itemstack.getItemDamage() + deltadamage > itemstack.getMaxDamage();
		itemstack.damageItem(deltadamage, entityplayer);
		if (flag)
		{
			int bayonetdamage = itemstack.stackTagCompound == null ? 0 : itemstack.stackTagCompound.getShort("bayonetDamage");
			entityplayer.inventory.addItemStackToInventory(new ItemStack(musket.bayonetItem, 1, bayonetdamage));
		} else
		{
			setReloadState(itemstack, ReloadHelper.STATE_NONE);
		}
		postShootingEffects(itemstack, entityplayer, world);
	}

	@Override
	public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world)
	{
		float f = entityplayer.isSneaking() ? -0.05F : -0.1F;
		double d = -MathHelper.sin((entityplayer.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((0 / 180F) * 3.141593F) * f;
		double d1 = MathHelper.cos((entityplayer.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((0 / 180F) * 3.141593F) * f;
		entityplayer.rotationPitch -= entityplayer.isSneaking() ? 7.5F : 15F;
		entityplayer.addVelocity(d, 0, d1);
	}

	@Override
	public void effectShoot(World world, double x, double y, double z, float yaw, float pitch)
	{
		world.playSoundEffect(x, y, z, "random.explode", 3F, 1F / (weapon.getItemRand().nextFloat() * 0.4F + 0.7F));
		world.playSoundEffect(x, y, z, "ambient.weather.thunder", 3F, 1F / (weapon.getItemRand().nextFloat() * 0.4F + 0.4F));

		float particleX = -MathHelper.sin(((yaw + 23) / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
		float particleY = -MathHelper.sin((pitch / 180F) * 3.141593F) - 0.1F;
		float particleZ = MathHelper.cos(((yaw + 23) / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);

		for (int i = 0; i < 3; i++)
		{
			world.spawnParticle("smoke", x + particleX, y + particleY, z + particleZ, 0.0D, 0.0D, 0.0D);
		}
		world.spawnParticle("flame", x + particleX, y + particleY, z + particleZ, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public float getMaxZoom()
	{
		return 0.15f;
	}
}
