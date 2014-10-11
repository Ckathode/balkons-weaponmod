package ckathode.weaponmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MeleeCompHalberd extends MeleeComponent implements IExtendedReachItem
{
	public static boolean getHalberdState(ItemStack itemstack)
	{
		if (itemstack.hasTagCompound())
		{
			return itemstack.getTagCompound().getBoolean("halb");
		}
		return false;
	}
	
	public static void setHalberdState(ItemStack itemstack, boolean flag)
	{
		if (!itemstack.hasTagCompound())
		{
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setBoolean("halb", flag);
	}
	
	private float	halberdEntityDamage;
	
	public MeleeCompHalberd(ToolMaterial toolmaterial)
	{
		super(MeleeSpecs.HALBERD, toolmaterial);
		halberdEntityDamage = getEntityDamage();
	}
	
	@Override
	public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		float kb = super.getKnockBack(itemstack, entityliving, attacker);
		return getHalberdState(itemstack) ? kb / 2F : kb;
	}
	
	@Override
	public float getEntityDamage()
	{
		return halberdEntityDamage;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.none;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		setHalberdState(itemstack, !getHalberdState(itemstack));
		return itemstack;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer entityplayer, Entity entity)
	{
		super.onLeftClickEntity(itemstack, entityplayer, entity);
		halberdEntityDamage = getHalberdState(itemstack) ? super.getEntityDamage() * 2F : super.getEntityDamage();
		return false;
	}
	
	@Override
	public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack)
	{
		return 4F;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldRotateAroundWhenRendering()
	{
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		ItemStack is = player.inventory.getCurrentItem();
		if (is != null && is.getItem() == item)
		{
			return getHalberdState(is);
		}
		return false;
	}
}
