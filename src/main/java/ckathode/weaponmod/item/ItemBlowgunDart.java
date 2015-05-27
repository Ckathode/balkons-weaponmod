package ckathode.weaponmod.item;

import java.util.List;

import ckathode.weaponmod.BalkonsWeaponMod;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlowgunDart extends WMItem
{
	public ItemBlowgunDart(String id)
	{
		super(id);
		ModelBakery.addVariantName(this, BalkonsWeaponMod.MOD_ID + ":" + id);
		ModelBakery.addVariantName(this, BalkonsWeaponMod.MOD_ID + ":" + id + "_hunger");
		ModelBakery.addVariantName(this, BalkonsWeaponMod.MOD_ID + ":" + id + "_slow");
		ModelBakery.addVariantName(this, BalkonsWeaponMod.MOD_ID + ":" + id + "_damage");
		setHasSubtypes(true);
	}
	public String getUnlocalizedName(ItemStack stack)
	{
		return this.getFullName(stack.getMetadata());
	}

	public String getUnlocalizedName(int metadata)
	{
		return this.getFullName(metadata);
	}

	public String getFullName(int metadata){
		if(metadata == 1){
			return super.getUnlocalizedName() + "_hunger";
		}else if(metadata == 2){
			return super.getUnlocalizedName() + "_slow";
		}else if(metadata == 3){
			return super.getUnlocalizedName() + "_damage";
		}
		return super.getUnlocalizedName();
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
