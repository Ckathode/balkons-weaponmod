package ckathode.weaponmod.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WarhammerExplosion;

public class MeleeCompWarhammer extends MeleeComponent
{
	public static final int	CHARGE_DELAY	= 400;	//ticks = tickrate * seconds = 20/s * 20s = 400
													
	public MeleeCompWarhammer(ToolMaterial toolmaterial)
	{
		super(MeleeSpecs.WARHAMMER, toolmaterial);
	}
	
	@Override
	public float getBlockDamage(ItemStack itemstack, Block block)
	{
		float f = super.getBlockDamage(itemstack, block);
		float f1 = weaponMaterial.getDamageVsEntity() + 2F;
		return f * f1;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		int j = getMaxItemUseDuration(itemstack) - i;
		float f = j / 20F;
		f = (f * f + f * 2.0F) / 4F;
		if (f > 1.0F)
		{
			superSmash(itemstack, world, entityplayer);
		}
	}
	
	protected void superSmash(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		entityplayer.swingItem();
		float f = getEntityDamage() / 2F;
		WarhammerExplosion expl = new WarhammerExplosion(world, entityplayer, entityplayer.posX, entityplayer.posY - entityplayer.getEyeHeight(), entityplayer.posZ, f);
		expl.doEntityExplosion(DamageSource.causePlayerDamage(entityplayer));
		expl.doParticleExplosion(true, false);
		itemstack.damageItem(16, entityplayer);
		entityplayer.addExhaustion(6F);
		setSmashed(entityplayer);
	}
	
	public void setSmashed(EntityPlayer entityplayer)
	{
		PlayerWeaponData.setLastWarhammerSmashTicks(entityplayer, entityplayer.ticksExisted);
	}
	
	public boolean isCharged(EntityPlayer entityplayer)
	{
		return entityplayer.ticksExisted > PlayerWeaponData.getLastWarhammerSmashTicks(entityplayer) + CHARGE_DELAY;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.bow;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 72000;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (itemstack.stackSize <= 0) return itemstack;
		if (isCharged(entityplayer))
		{
			int i = getMaxItemUseDuration(itemstack);
			entityplayer.setItemInUse(itemstack, i);
		}
		return itemstack;
	}
}
