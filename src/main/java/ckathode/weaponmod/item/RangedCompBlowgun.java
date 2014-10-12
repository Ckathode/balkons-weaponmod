package ckathode.weaponmod.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;

public class RangedCompBlowgun extends RangedComponent
{
	public RangedCompBlowgun()
	{
		super(RangedSpecs.BLOWGUN);
	}

	@Override
	public void effectReloadDone(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		entityplayer.swingItem();
		world.playSoundAtEntity(entityplayer, "random.click", 0.8F, 1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.4F));
	}

	@Override
	public int getReloadDuration(ItemStack itemstack)
	{
		return BalkonsWeaponMod.instance.modConfig.getReloadTime("blowgun");
	}

	@Override
	public void fire(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		int j = getMaxItemUseDuration(itemstack) - i;
		float f = j / 20F;
		f = (f * f + f * 2.0F) / 3F;
		if (f < 0.1F) return;
		if (f > 1.0F)
		{
			f = 1.0F;
		}

		ItemStack dartstack = null;
		int k;
		for (k = 0; k < entityplayer.inventory.mainInventory.length; k++)
		{
			ItemStack is = entityplayer.inventory.mainInventory[k];
			if (is != null && is.getItem() == getAmmoItem())
			{
				dartstack = is;
				break;
			}
		}

		if (dartstack == null && entityplayer.capabilities.isCreativeMode)
		{
			dartstack = new ItemStack(BalkonsWeaponMod.dart, 1, DartType.damage.typeID);
		}

		if (!entityplayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) == 0)
		{
			if (--dartstack.stackSize == 0)
			{
				entityplayer.inventory.mainInventory[k] = null;
			}
		}

		if (!world.isRemote)
		{
			EntityBlowgunDart entity = new EntityBlowgunDart(world, entityplayer, f * 1.5F);
			entity.setDartEffectType(dartstack.getItemDamage());
			applyProjectileEnchantments(entity, itemstack);
			world.spawnEntityInWorld(entity);
		}

		int damage = 1;
		if (itemstack.getItemDamage() + damage <= itemstack.getMaxDamage())
		{
			setReloadState(itemstack, ReloadHelper.STATE_NONE);
		}
		itemstack.damageItem(damage, entityplayer);

		postShootingEffects(itemstack, entityplayer, world);
		setReloadState(itemstack, ReloadHelper.STATE_NONE);
	}

	@Override
	public boolean hasAmmoAndConsume(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return hasAmmo(itemstack, world, entityplayer);
	}

	@Override
	public void soundEmpty(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (weapon.getItemRand().nextFloat() * 0.2F + 0.5F));
	}

	@Override
	public void soundCharge(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		world.playSoundAtEntity(entityplayer, "random.breath", 1.0F, 1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.8F));
	}

	@Override
	public void effectShoot(World world, double x, double y, double z, float yaw, float pitch)
	{
		world.playSoundEffect(x, y, z, "random.bow", 1.0F, 1.0F / (weapon.getItemRand().nextFloat() * 0.2F + 0.5F));

		float particleX = -MathHelper.sin(((yaw + 23) / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
		float particleY = -MathHelper.sin((pitch / 180F) * 3.141593F) - 0.1F;
		float particleZ = MathHelper.cos(((yaw + 23) / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);

		world.spawnParticle("explode", x + particleX, y + particleY, z + particleZ, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world)
	{
	}
	
	@Override
	public float getMaxZoom()
	{
		return 0.1f;
	}
}
