package ckathode.weaponmod.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFlail extends WMItem
{
	public IIcon	iconIndexThrown;
	private float	flailDamage;
	
	public ItemFlail(String id, Item.ToolMaterial toolmaterial)
	{
		super(id);
		setMaxDamage(toolmaterial.getMaxUses());
		flailDamage = 4F + toolmaterial.getDamageVsEntity() * 1F;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
		if (!(entity instanceof EntityPlayer)) return;
		EntityPlayer player = ((EntityPlayer) entity);
		if (!PlayerWeaponData.isFlailThrown(player)) return;
		
		ItemStack itemstack2 = player.getCurrentEquippedItem();
		if (itemstack2 == null || itemstack2.getItem() != this)
		{
			setThrown(player, false);
		} else
		{
			int id = PlayerWeaponData.getFlailEntityId(player);
			if (id != 0)
			{
				Entity entity1 = world.getEntityByID(id);
				if (entity1 instanceof EntityFlail)
				{
					((EntityFlail) entity1).shootingEntity = player;
					((EntityFlail) entity1).setThrownItemStack(itemstack);
				}
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		removePreviousFlail(world, entityplayer);
		
		if (itemstack.stackSize > 0)
		{
			entityplayer.swingItem();
			
			itemstack.damageItem(1, entityplayer);
			throwFlail(itemstack, world, entityplayer);
		}
		
		return itemstack;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer entityplayer, Entity entity)
	{
		onItemRightClick(itemstack, entityplayer.worldObj, entityplayer);
		return false;
	}
	
	public void throwFlail(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isRemote)
		{
			EntityFlail entity = new EntityFlail(world, entityplayer, itemstack);
			PlayerWeaponData.setFlailEntityId(entityplayer, entity.getEntityId());
			world.spawnEntityInWorld(entity);
		}
		
		setThrown(entityplayer, true);
	}
	
	public void setThrown(EntityPlayer entityplayer, boolean flag)
	{
		PlayerWeaponData.setFlailThrown(entityplayer, flag);
	}
	
	private void removePreviousFlail(World world, EntityPlayer entityplayer)
	{
		int id = PlayerWeaponData.getFlailEntityId(entityplayer);
		if (id != 0)
		{
			Entity entity = world.getEntityByID(id);
			if (entity instanceof EntityFlail)
			{
				entity.setDead();
			}
		}
	}
	
	public boolean getThrown(EntityPlayer entityplayer)
	{
		return PlayerWeaponData.isFlailThrown(entityplayer);
	}
	
	public float getFlailDamage()
	{
		return flailDamage;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if (PlayerWeaponData.isFlailThrown(player))
		{
			return iconIndexThrown;
		}
		return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister)
	{
		super.registerIcons(iconregister);
		iconIndexThrown = iconregister.registerIcon("weaponmod:flail-thrown");
	}
}
