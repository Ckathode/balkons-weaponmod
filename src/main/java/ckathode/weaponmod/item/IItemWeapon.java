package ckathode.weaponmod.item;

import java.util.Random;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IItemWeapon
{
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity);
	
	public UUID getUUID();
	
	public Random getItemRand();
	
	public MeleeComponent getMeleeComponent();
	
	public RangedComponent getRangedComponent();
}
