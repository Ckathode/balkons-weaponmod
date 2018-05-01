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
	public static DartType		normal, hunger, slow, posion, blind, nausea;
	
	static
	{
		normal = new DartType(0, "dart", "stickWood", new PotionEffect(Potion.weakness.id, 100, 0));
		hunger = new DartType(1, "dart_hunger", Items.rotten_flesh, new PotionEffect(Potion.hunger.id, 600, 1));
		slow = new DartType(2, "dart_slow", Items.slime_ball, new PotionEffect(Potion.moveSlowdown.id, 600, 1));
		posion = new DartType(3, "dart_damage", Items.spider_eye, new PotionEffect(Potion.poison.id, 300, 1));
		blind = new DartType(4, "dart_blind", Items.dye, new PotionEffect(Potion.blindness.id, 120, 0));
		nausea = new DartType(5, "dart_nausea", Blocks.red_mushroom, new PotionEffect(Potion.confusion.id, 600, 0));
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
