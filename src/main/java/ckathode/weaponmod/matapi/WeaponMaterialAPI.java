package ckathode.weaponmod.matapi;

import java.util.EnumMap;

import net.minecraft.item.Item;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = WeaponMaterialAPI.MOD_ID, name = WeaponMaterialAPI.MOD_NAME, version = WeaponMaterialAPI.MOD_VERSION)
public class WeaponMaterialAPI
{
	public static final String								MOD_ID					= "bwmMaterialAPI";
	public static final String								MOD_NAME				= "Balkon's WeaponMod Material API";
	public static final String								MOD_VERSION				= "1.7.10 v0.1.0";
	
	@Instance(MOD_ID)
	public static WeaponMaterialAPI							instance;
	public static Logger									modLog;
	
	@SideOnly(Side.CLIENT)
	private static final float[]							DEFAULT_MATERIAL_COLOR	= { 1F, 1F, 1F, 1F };
	
	private EnumMap<Item.ToolMaterial, IWeaponMaterials>	materialToModMap;
	
	@EventHandler
	public void preInitMod(FMLPreInitializationEvent e)
	{
		materialToModMap = new EnumMap<Item.ToolMaterial, IWeaponMaterials>(Item.ToolMaterial.class);
		modLog = e.getModLog();
	}
	
	@EventHandler
	public void onIMCMessage(FMLInterModComms.IMCEvent e)
	{
		for (IMCMessage m : e.getMessages())
		{
			if ("register".equals(m.key))
			{
				ModContainer mc = FMLCommonHandler.instance().findContainerFor(m.getSender());
				if (mc == null)
				{
					modLog.error("Cannot find ModContainer for " + m.getSender());
				} else if (mc.getMod() instanceof IWeaponMaterials)
				{
					modLog.info("Registering weapon materials for " + mc.getName());
					registerWeaponMaterials((IWeaponMaterials) mc.getMod());
				} else
				{
					modLog.error("Mod " + m.getSender() + " -> " + mc.getMod() + " is not of type IWeaponMaterials");
				}
			} else if ("unregister".equals(m.key))
			{
				modLog.error("unregister not implemented");
			}
		}
	}
	
	private void registerWeaponMaterials(IWeaponMaterials weaponmaterials)
	{
		for (Item.ToolMaterial tm : weaponmaterials.getWMCustomMaterials())
		{
			materialToModMap.put(tm, weaponmaterials);
		}
	}
	
	public IWeaponMaterials getModFromMaterial(Item.ToolMaterial toolmaterial)
	{
		return materialToModMap.get(toolmaterial);
	}
	
	@SideOnly(Side.CLIENT)
	public float[] getWeaponMaterialColorFromID(int id)
	{
		if (id < 0)
		{
			throw new IllegalArgumentException("ID < 0");
		}
		Item.ToolMaterial[] mats = Item.ToolMaterial.values();
		if (id < mats.length)
		{
			Item.ToolMaterial tm = mats[id];
			IWeaponMaterials mod = getModFromMaterial(tm);
			if (mod != null)
			{
				float[] clr = mod.getWMProjectileColor(tm);
				if (clr == null)
				{
					modLog.error("Received null color for " + tm.name() + " from " + mod.toString());
					return DEFAULT_MATERIAL_COLOR;
				}
				if (clr.length != 4)
				{
					modLog.error("Received color float array with a length of " + clr.length + " for " + tm.name() + " from " + mod.toString() + ", while 4 is expected");
					return DEFAULT_MATERIAL_COLOR;
				}
				return clr;
			}
			modLog.warn("ToolMaterial " + tm.name() + " has not been registered");
		}
		return DEFAULT_MATERIAL_COLOR;
	}
}
