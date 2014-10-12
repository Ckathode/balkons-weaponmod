package ckathode.weaponmod.item;

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
	
	public MeleeCompHalberd(ToolMaterial toolmaterial)
	{
		super(MeleeSpecs.HALBERD, toolmaterial);
	}
	
	@Override
	public int getAttackDelay(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		int ad = super.getAttackDelay(itemstack, entityliving, attacker);
		return getHalberdState(itemstack) ? 0 : ad;
	}
	
	@Override
	public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		float kb = super.getKnockBack(itemstack, entityliving, attacker);
		return getHalberdState(itemstack) ? kb / 2F : kb;
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
