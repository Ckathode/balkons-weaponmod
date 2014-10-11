package ckathode.weaponmod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WMLanguage
{
	private int	languageAmount;
	
	public WMLanguage()
	{
		languageAmount = 0;
	}
	
	public int getLoadedLanguageAmount()
	{
		return languageAmount;
	}
	
	private void onLanguageLoaded(String name)
	{
		languageAmount++;
		BalkonsWeaponMod.modLog.trace("Language " + name + " loaded succesfully.");
	}
	
	public void loadAllLanguages() throws IOException
	{
		loadDefaultLanguage();
		
		List<String> langs = new ArrayList<String>();
		InputStream is = WMLanguage.class.getClassLoader().getResourceAsStream("assets/weaponmod/lang/lang.txt");
		if (is == null) throw new FileNotFoundException("/lang/lang.txt file has not been found, please redownload the mod");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				langs.add(line);
			}
		} catch (IOException e)
		{
			throw e;
		} finally
		{
			reader.close();
		}
		
		for (String lang : langs)
		{
			BalkonsWeaponMod.modLog.trace("Loading language " + lang);
			try
			{
				loadLanguageFromFile(lang);
			} catch (FileNotFoundException e)
			{
				BalkonsWeaponMod.modLog.trace("Language file for " + lang + " not found");
			}
		}
	}
	
	public void loadLanguageFromFile(String langname) throws IOException
	{
		Properties props = new Properties();
		InputStream in = WMLanguage.class.getClassLoader().getResourceAsStream("assets/weaponmod/lang/" + langname + ".lang");
		if (in == null) throw new FileNotFoundException(langname);
		
		props.load(in);
		in.close();
		
		loadLanguage(props, langname);
	}
	
	public void loadLanguage(Properties props, String langname)
	{
		for (Entry<Object, Object> entry : props.entrySet())
		{
			LanguageRegistry.instance().addStringLocalization((String) entry.getKey(), langname, (String) entry.getValue());
		}
		onLanguageLoaded(langname);
	}
	
	public void loadDefaultLanguage()
	{
		Properties props = new Properties();
		props.setProperty("item.spear.wood.name", "Wooden Spear");
		props.setProperty("item.spear.stone.name", "Stone Spear");
		props.setProperty("item.spear.iron.name", "Iron Spear");
		props.setProperty("item.spear.diamond.name", "Diamond Spear");
		props.setProperty("item.spear.gold.name", "Golden Spear");
		
		props.setProperty("item.halberd.wood.name", "Wooden Halberd");
		props.setProperty("item.halberd.stone.name", "Stone Halberd");
		props.setProperty("item.halberd.iron.name", "Iron Halberd");
		props.setProperty("item.halberd.diamond.name", "Diamond Halberd");
		props.setProperty("item.halberd.gold.name", "Golden Halberd");
		
		props.setProperty("item.battleaxe.wood.name", "Wooden Battleaxe");
		props.setProperty("item.battleaxe.stone.name", "Stone Battleaxe");
		props.setProperty("item.battleaxe.iron.name", "Iron Battleaxe");
		props.setProperty("item.battleaxe.diamond.name", "Diamond Battleaxe");
		props.setProperty("item.battleaxe.gold.name", "Golden Battleaxe");
		
		props.setProperty("item.warhammer.wood.name", "Wooden Warhammer");
		props.setProperty("item.warhammer.stone.name", "Stone Warhammer");
		props.setProperty("item.warhammer.iron.name", "Iron Warhammer");
		props.setProperty("item.warhammer.diamond.name", "Diamond Warhammer");
		props.setProperty("item.warhammer.gold.name", "Golden Warhammer");
		
		props.setProperty("item.knife.wood.name", "Wooden Knife");
		props.setProperty("item.knife.stone.name", "Stone Knife");
		props.setProperty("item.knife.iron.name", "Iron Knife");
		props.setProperty("item.knife.diamond.name", "Diamond Knife");
		props.setProperty("item.knife.gold.name", "Golden Knife");
		
		props.setProperty("item.flail.wood.name", "Wooden Flail");
		props.setProperty("item.flailstone.name", "Stone Flail");
		props.setProperty("item.flailiron.name", "Iron Flail");
		props.setProperty("item.flaildiamond.name", "Diamond Flail");
		props.setProperty("item.flailgold.name", "Golden Flail");
		
		props.setProperty("item.boomerang.wood.name", "Wooden Boomerang");
		props.setProperty("item.boomerang.stone.name", "Stone Boomerang");
		props.setProperty("item.boomerang.iron.name", "Iron Boomerang");
		props.setProperty("item.boomerang.diamond.name", "Diamond Boomerang");
		props.setProperty("item.boomerang.gold.name", "Golden Boomerang");
		
		props.setProperty("item.katana.wood.name", "Wooden Katana");
		props.setProperty("item.katana.stone.name", "Stone Katana");
		props.setProperty("item.katana.iron.name", "Iron Katana");
		props.setProperty("item.katana.diamond.name", "Diamond Katana");
		props.setProperty("item.katana.gold.name", "Golden Katana");
		
		props.setProperty("item.javelin.name", "Javelin");
		
		props.setProperty("item.musket.name", "Musket");
		props.setProperty("item.musketbayonet.name", "Musket with Bayonet");
		props.setProperty("item.musket-ironpart.name", "Musket Barrel");
		props.setProperty("item.bullet.name", "Musket Round");
		
		props.setProperty("item.musketbayonet.wood.name", "Musket with Wooden Bayonet");
		props.setProperty("item.musketbayonet.stone.name", "Musket with Stone Bayonet");
		props.setProperty("item.musketbayonet.iron.name", "Musket with Iron Bayonet");
		props.setProperty("item.musketbayonet.diamond.name", "Musket with Diamond Bayonet");
		props.setProperty("item.musketbayonet.gold.name", "Musket with Golden Bayonet");
		
		props.setProperty("item.crossbow.name", "Crossbow");
		props.setProperty("item.bolt.name", "Crossbow Bolt");
		
		props.setProperty("item.blowgun.name", "Blowgun");
		props.setProperty("item.dart.name", "Poisonous Dart");
		
		props.setProperty("item.dynamite.name", "Dynamite");
		
		props.setProperty("item.firerod.name", "Fire Rod");
		
		props.setProperty("item.cannon.name", "Cannon");
		props.setProperty("item.cannonball.name", "Cannon Ball");
		
		props.setProperty("item.blunderbuss.name", "Blunderbuss");
		props.setProperty("item.blunder-ironpart.name", "Blunderbuss Barrel");
		props.setProperty("item.shot.name", "Blunderbuss Shot");
		
		props.setProperty("item.flintlock.name", "Flintlock Pistol");
		
		props.setProperty("item.dummy.name", "Training Dummy");
		
		props.setProperty("item.gun-stock.name", "Gun Stock");
		
		props.setProperty("attribute.name.weaponmod.ignoreArmour", "Ignore Armor");
		props.setProperty("attribute.name.weaponmod.knockback", "Knockback");
		props.setProperty("attribute.name.weaponmod.attackSpeed", "Attack Speed");
		props.setProperty("attribute.name.weaponmod.reloadTime", "Reload Time");
		
		loadLanguage(props, "en_US");
	}
}
