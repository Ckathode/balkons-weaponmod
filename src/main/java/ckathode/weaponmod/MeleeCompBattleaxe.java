package ckathode.weaponmod.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import ckathode.weaponmod.DamageSourceAxe;
import ckathode.weaponmod.WeaponModAttributes;

import com.google.common.collect.Multimap;

public class MeleeCompBattleaxe extends MeleeComponent
{
	public static final int[]	DEFAULT_IGNORES	= { 1, 1, 1, 1, 1 };

	public int					ignoreArmourAmount;

	public MeleeCompBattleaxe(ToolMaterial toolmaterial)
	{
		super(MeleeSpecs.BATTLEAXE, toolmaterial);
		if (toolmaterial.ordinal() < DEFAULT_IGNORES.length)
		{
			ignoreArmourAmount = DEFAULT_IGNORES[toolmaterial.ordinal()];
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) entity;

			double mx = entity.motionX;
			double my = entity.motionY;
			double mz = entity.motionZ;
			int prevhurtres = living.hurtResistantTime;
			int prevhurt = living.hurtTime;
			living.attackEntityFrom(new DamageSourceAxe(), ignoreArmourAmount);
			entity.motionX = mx;
			entity.motionY = my;
			entity.motionZ = mz;
			living.hurtResistantTime = prevhurtres;
			living.hurtTime = prevhurt;
		}

		return super.onLeftClickEntity(itemstack, player, entity);
	}

	@Override
	public float getBlockDamage(ItemStack itemstack, Block block)
	{
		return block.getMaterial() == Material.wood ? (weaponMaterial.getEfficiencyOnProperMaterial() * 0.75F) : super.getBlockDamage(itemstack, block);
	}

	@Override
	public boolean canHarvestBlock(Block block)
	{
		return block.getMaterial() == Material.wood;
	}

	@Override
	public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap)
	{
		super.addItemAttributeModifiers(multimap);
		multimap.put(WeaponModAttributes.IGNORE_ARMOUR_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(weapon.getUUID(), "Weapon ignore armour modifier", ignoreArmourAmount, 0));
	}
}
