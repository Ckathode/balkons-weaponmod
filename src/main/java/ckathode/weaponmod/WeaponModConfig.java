package ckathode.weaponmod;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;

public class WeaponModConfig
{
	private final Configuration				config;
	
	public boolean							cannonDoesBlockDamage, dynamiteDoesBlockDamage;
	public boolean							canThrowKnife, canThrowSpear;
	public boolean							allCanPickup;
	
	private Map<String, EnableSetting>		enableSettings;
	private Map<String, ReloadTimeSetting>	reloadTimeSettings;
	
	public WeaponModConfig(Configuration configuration)
	{
		config = configuration;
		enableSettings = new LinkedHashMap<String, EnableSetting>();
		reloadTimeSettings = new LinkedHashMap<String, ReloadTimeSetting>();
	}
	
	public void addEnableSetting(String weapon)
	{
		enableSettings.put(weapon, new EnableSetting(weapon));
	}
	
	public void addReloadTimeSetting(String weapon, int defaulttime)
	{
		reloadTimeSettings.put(weapon, new ReloadTimeSetting(weapon, defaulttime));
	}
	
	public boolean isEnabled(String weapon)
	{
		EnableSetting es = enableSettings.get(weapon);
		return es == null || es.enabled;
	}
	
	public int getReloadTime(String weapon)
	{
		ReloadTimeSetting rs = reloadTimeSettings.get(weapon);
		return rs == null ? 0 : rs.reloadTime;
	}
	
	public void loadConfig()
	{
		config.load();
		
		config.addCustomCategoryComment("enable", "Enable or disable certain weapons");
		config.addCustomCategoryComment("reloadtime", "The reload durations of the reloadable weapons");
		config.addCustomCategoryComment("settings", "Miscellaneous mod settings");
		
		cannonDoesBlockDamage = config.get("settings", "cannon-block-damage", true).getBoolean(true);
		dynamiteDoesBlockDamage = config.get("settings", "dynamite-block-damage", true).getBoolean(true);
		canThrowKnife = config.get("settings", "can-throw-knife", true).getBoolean(true);
		canThrowSpear = config.get("settings", "can-throw-spear", true).getBoolean(true);
		allCanPickup = config.get("settings", "pickup-all", true, "Change this to 'false' to allow only the thrower/shooter of the projectile to pick the item up. If set to 'true' everyone can pick the item up.").getBoolean(true);
		
		for (EnableSetting es : enableSettings.values())
		{
			es.enabled = config.get("enable", es.settingName, es.enabled).getBoolean(es.enabled);
		}
		for (ReloadTimeSetting rs : reloadTimeSettings.values())
		{
			rs.reloadTime = config.get("reloadtime", rs.settingName, rs.reloadTime).getInt(rs.reloadTime);
		}
		
		config.save();
	}
	
	private static abstract class Setting
	{
		final String	settingName;
		
		Setting(String name)
		{
			settingName = name;
		}
	}
	
	private static class ReloadTimeSetting extends Setting
	{
		int	reloadTime;
		
		ReloadTimeSetting(String name, int time)
		{
			super(name + ".reloadtime");
			reloadTime = time;
		}
	}
	
	private static class EnableSetting extends Setting
	{
		boolean	enabled;
		
		EnableSetting(String name)
		{
			super(name + ".enabled");
			enabled = true;
		}
		
	}
}
