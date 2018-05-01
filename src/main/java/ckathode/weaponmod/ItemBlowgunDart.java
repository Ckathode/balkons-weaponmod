package ckathode.weaponmod.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlowgunDart extends WMItem
{
	public ItemBlowgunDart(String id)
	{
		super(id);
		setHasSubtypes(true);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs tab, @SuppressWarnings("rawtypes") List list)
	{
		for (int j = 0; j < DartType.dartTypes.length; ++j)
		{
			if (DartType.dartTypes[j] != null)
			{
				list.add(new ItemStack(item, 1, j));
			}
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int damage)
	{
		return (damage >= 0 && damage < DartType.dartTypes.length && DartType.dartTypes[damage] != null) ? DartType.dartTypes[damage].itemIcon : itemIcon;
	}
	
	@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon(getIconString());
		for (DartType type : DartType.dartTypes)
		{
			if (type != null)
			{
				type.itemIcon = iconregister.registerIcon("weaponmod:" + type.typeName);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, @SuppressWarnings("rawtypes") List list, boolean flag)
	{
		DartType type = DartType.getDartTypeFromStack(itemstack);
		if (type == null) return;
		PotionEffect potioneffect = type.potionEffect;
		Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
		
		String s = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
		
		/*
		Map map = potion.func_111186_k();
		
		if (map != null && map.size() > 0)
		{
			Iterator iterator1 = map.entrySet().iterator();
			
			while (iterator1.hasNext())
			{
				Entry entry = (Entry) iterator1.next();
				AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();
				AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.func_111166_b(), potion.func_111183_a(potioneffect.getAmplifier(), attributemodifier), attributemodifier.func_111169_c());
				hashmultimap.put(((Attribute) entry.getKey()).func_111108_a(), attributemodifier1);
			}
		}
		*/
		
		if (potioneffect.getAmplifier() > 0)
		{
			s += " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
		}
		
		if (potioneffect.getDuration() > 20)
		{
			s += " (" + Potion.getDurationString(potioneffect) + ")";
		}
		
		if (potion.isBadEffect())
		{
			list.add(EnumChatFormatting.RED + s);
		} else
		{
			list.add(EnumChatFormatting.GRAY + s);
		}
	}
}
