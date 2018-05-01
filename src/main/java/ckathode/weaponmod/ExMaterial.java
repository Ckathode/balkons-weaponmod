package ckathode.weaponmod;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.common.util.EnumHelper;

public class ExMaterial {
	
	public ExMaterial()
	{
		
		List<ItemStack> ingotSilver = OreDictionary.getOres("ingotSilver");
		
        for (ItemStack itemStack : ingotSilver)
        {
            OreDictionary.registerOre("ingotSilver", itemStack);
        }
        
	}

	private static Item.ToolMaterial getSilverMaterial() {
        if (Loader.isModLoaded("fusion")) return Item.ToolMaterial.valueOf("SILVER");
        if (Loader.isModLoaded("ThermalFoundation")) return Item.ToolMaterial.valueOf("TF:SILVER");
        return EnumHelper.addToolMaterial("BW|SILVER", 2, 500, 6f, 2f, 15);
    }

}
