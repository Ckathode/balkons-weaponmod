package ckathode.weaponmod.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityKnife;

public class MeleeCompKnife extends MeleeComponent
{
	public MeleeCompKnife(ToolMaterial toolmaterial)
	{
		super(MeleeSpecs.KNIFE, toolmaterial);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (!BalkonsWeaponMod.instance.modConfig.canThrowKnife) return super.onItemRightClick(itemstack, world, entityplayer);
		
		world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.8F));
		if (!world.isRemote)
		{
			EntityKnife entity = new EntityKnife(world, entityplayer, itemstack);
			entity.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemstack));
			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemstack) > 0)
			{
				entity.setFire(100);
			}
			world.spawnEntityInWorld(entity);
		}
		
		if (!entityplayer.capabilities.isCreativeMode)
		{
			itemstack = itemstack.copy();
			itemstack.stackSize = 0;
		}
		return itemstack;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return BalkonsWeaponMod.instance.modConfig.canThrowKnife ? EnumAction.NONE : super.getItemUseAction(itemstack);
	}
	
	@Override
	public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}
}
