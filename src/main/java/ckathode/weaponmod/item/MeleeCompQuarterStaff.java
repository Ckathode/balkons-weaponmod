package ckathode.weaponmod.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import ckathode.weaponmod.WeaponModAttributes;
import ckathode.weaponmod.item.MeleeComponent.MeleeSpecs;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MeleeCompQuarterStaff extends MeleeComponent implements IExtendedReachItem
{
	public float					blockmount;
	
	public float					blockextra;
	
	public MeleeCompQuarterStaff(ToolMaterial toolmaterial)
	{
		super(MeleeSpecs.QUARTERSTAFF, toolmaterial);
		
		blockmount = toolmaterial.getDamageVsEntity() > 0 ? toolmaterial.getDamageVsEntity()  + 1F : 1F ;
		
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
		int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
		if (knockback > 0)
		{
			blockextra = knockback;
		}
		else blockextra = 0;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.block;
	}
	
	@Override
	public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack)
	{
		return 8F;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldRotateAroundWhenRendering()
	{
		return false;
	}
	
	@Override
	public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap)
	{
		super.addItemAttributeModifiers(multimap);
		multimap.put(WeaponModAttributes.BLOCK_EFFECT.getAttributeUnlocalizedName(), new AttributeModifier(weapon.getUUID(), "Block reduce damage modifier", blockmount + blockextra, 0));
	}
}
