package ckathode.weaponmod.matapi;

import java.util.EnumMap;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = WeaponMaterialAPI.MOD_ID, name = WeaponMaterialAPI.MOD_NAME, version = WeaponMaterialAPI.MOD_VERSION)
public class WeaponMaterialAPI
{
	public static final String							MOD_ID					= "bwmMaterialAPI";
	public static final String							MOD_NAME				= "Balkon's WeaponMod Material API";
	public static final String							MOD_VERSION				= "v0.1.0";
	
	@Instance(MOD_ID)
	public static WeaponMaterialAPI						instance;
	public static Logger								modLog;
	
	@SideOnly(Side.CLIENT)
	private static final float[]						DEFAULT_MATERIAL_COLOR	= { 1F, 1F, 1F, 1F };
	
	private EnumMap<Item.ToolMaterial, WeaponMaterial>	materialMap;
	
	@EventHandler
	public void preInitMod(FMLPreInitializationEvent e)
	{
		materialMap = new EnumMap<Item.ToolMaterial, WeaponMaterial>(Item.ToolMaterial.class);
		modLog = e.getModLog();
	}
	
	@EventHandler
	public void onIMCMessage(FMLInterModComms.IMCEvent e)
	{
		for (IMCMessage m : e.getMessages())
		{
			if ("register".equals(m.key))
			{
				if (!m.isNBTMessage())
				{
					modLog.error("Please provide an NBTTagCompound to register");
					continue;
				}
				NBTTagCompound compound = m.getNBTValue();
				if (!compound.hasKey("ordinal"))
				{
					modLog.error("NBTTagCompound does not have an integer with key='ordinal'");
					continue;
				}
				int id = compound.getInteger("ordinal");
				int color = compound.hasKey("projectileColor") ? compound.getInteger("projectileColor") : 0xFFFFFFFF;
				float knockback = compound.hasKey("knockbackMult") ? compound.getFloat("knockbackMult") : 1f;
				Item.ToolMaterial toolmaterial;
				try
				{
					toolmaterial = Item.ToolMaterial.values()[id];
				} catch (ArrayIndexOutOfBoundsException aioobe)
				{
					modLog.error("No ToolMaterial with ordinal " + id + " has been registered");
					continue;
				}
				
				WeaponMaterial wm = new WeaponMaterial(toolmaterial, color, knockback);
				materialMap.put(toolmaterial, wm);
				/*
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
				}*/
				
				modLog.info("Registered WeaponMaterial " + wm.toString());
			} else if ("unregister".equals(m.key))
			{
				int id;
				if (m.isStringMessage())
				{
					try
					{
						id = Integer.parseInt(m.getStringValue());
					} catch (NumberFormatException nfe)
					{
						modLog.error("Cannot parse string message to integer", nfe);
						continue;
					}
					
				} else if (m.isNBTMessage())
				{
					id = m.getNBTValue().getInteger("ordinal");
				} else
				{
					modLog.error("Please provide a String or an NBTTagCompound to unregister");
					continue;
				}
				Item.ToolMaterial toolmaterial;
				try
				{
					toolmaterial = Item.ToolMaterial.values()[id];
				} catch (ArrayIndexOutOfBoundsException aioobe)
				{
					modLog.error("No ToolMaterial with ordinal " + id + " has been registered");
					continue;
				}
				materialMap.remove(toolmaterial);
			}
		}
	}
	
	public WeaponMaterial getWeaponMaterial(Item.ToolMaterial toolmaterial)
	{
		return materialMap.get(toolmaterial);
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
			WeaponMaterial mod = getWeaponMaterial(tm);
			if (mod != null)
			{
				return mod.getProjectileColor();
			}
			modLog.warn("ToolMaterial " + tm.name() + " has not been registered");
		}
		return DEFAULT_MATERIAL_COLOR;
	}
}
