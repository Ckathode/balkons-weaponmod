package ckathode.weaponmod.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public class DartType
{
	public static DartType[]	dartTypes	= new DartType[128];
	public static DartType		damage, hunger, slow, damage2;
	
	static
	{
		damage = new DartType(0, "dart", Blocks.cactus, new PotionEffect(Potion.poison.id, 120, 0));
		hunger = new DartType(1, "dart_hunger", Items.rotten_flesh, new PotionEffect(Potion.hunger.id, 360, 0));
		slow = new DartType(2, "dart_slow", Items.slime_ball, new PotionEffect(Potion.moveSlowdown.id, 360, 1));
		damage2 = new DartType(3, "dart_damage", Items.spider_eye, new PotionEffect(Potion.poison.id, 120, 1));
	}
	
	public static DartType getDartTypeFromStack(ItemStack itemstack)
	{
		int damage = itemstack.getItemDamage();
		if (damage >= 0 && damage < dartTypes.length)
		{
			return dartTypes[damage];
		}
		return null;
	}
	
	public final int	typeID;
	public final String	typeName;
	
	public Object		craftItem;
	public PotionEffect	potionEffect;
	
	public IIcon		itemIcon;
	
	public DartType(int id, String typename, Object craftitem, PotionEffect potioneffect)
	{
		dartTypes[id] = this;
		typeID = id;
		typeName = typename;
		craftItem = craftitem;
		potionEffect = potioneffect;
		
		itemIcon = null;
	}
	
}
