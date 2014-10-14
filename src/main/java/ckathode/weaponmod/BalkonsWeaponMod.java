package ckathode.weaponmod;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.apache.logging.log4j.Logger;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.entity.EntityDummy;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import ckathode.weaponmod.entity.projectile.dispense.DispenseBlowgunDart;
import ckathode.weaponmod.entity.projectile.dispense.DispenseBlunderShot;
import ckathode.weaponmod.entity.projectile.dispense.DispenseCannonBall;
import ckathode.weaponmod.entity.projectile.dispense.DispenseCrossbowBolt;
import ckathode.weaponmod.entity.projectile.dispense.DispenseDynamite;
import ckathode.weaponmod.entity.projectile.dispense.DispenseJavelin;
import ckathode.weaponmod.entity.projectile.dispense.DispenseMusketBullet;
import ckathode.weaponmod.item.DartType;
import ckathode.weaponmod.item.ItemBlowgunDart;
import ckathode.weaponmod.item.ItemCannon;
import ckathode.weaponmod.item.ItemCrossbow;
import ckathode.weaponmod.item.ItemDummy;
import ckathode.weaponmod.item.ItemDynamite;
import ckathode.weaponmod.item.ItemFlail;
import ckathode.weaponmod.item.ItemJavelin;
import ckathode.weaponmod.item.ItemMelee;
import ckathode.weaponmod.item.ItemMusket;
import ckathode.weaponmod.item.ItemShooter;
import ckathode.weaponmod.item.MeleeCompBattleaxe;
import ckathode.weaponmod.item.MeleeCompBoomerang;
import ckathode.weaponmod.item.MeleeCompFirerod;
import ckathode.weaponmod.item.MeleeCompHalberd;
import ckathode.weaponmod.item.MeleeCompKnife;
import ckathode.weaponmod.item.MeleeCompNone;
import ckathode.weaponmod.item.MeleeCompSpear;
import ckathode.weaponmod.item.MeleeCompWarhammer;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.MeleeComponent.MeleeSpecs;
import ckathode.weaponmod.item.RangedCompBlowgun;
import ckathode.weaponmod.item.RangedCompBlunderbuss;
import ckathode.weaponmod.item.RangedCompCrossbow;
import ckathode.weaponmod.item.RangedCompFlintlock;
import ckathode.weaponmod.item.WMItem;
import ckathode.weaponmod.network.WMMessagePipeline;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = BalkonsWeaponMod.MOD_ID, name = BalkonsWeaponMod.MOD_NAME, version = BalkonsWeaponMod.MOD_VERSION)
public class BalkonsWeaponMod
{
	public static final String		MOD_ID		= "weaponmod";
	public static final String		MOD_NAME	= "Balkon's WeaponMod";
	public static final String		MOD_VERSION	= "v1.14.3";
	
	@Instance("weaponmod")
	public static BalkonsWeaponMod	instance;
	
	public static Logger			modLog;
	
	@SidedProxy(clientSide = "ckathode.weaponmod.WMClientProxy", serverSide = "ckathode.weaponmod.WMCommonProxy")
	public static WMCommonProxy		proxy;
	
	public static Item				javelin;
	
	public static Item				spearWood;
	public static Item				spearStone;
	public static Item				spearSteel;
	public static Item				spearDiamond;
	public static Item				spearGold;
	
	public static Item				halberdWood;
	public static Item				halberdStone;
	public static Item				halberdSteel;
	public static Item				halberdDiamond;
	public static Item				halberdGold;
	
	public static Item				knifeWood;
	public static Item				knifeStone;
	public static Item				knifeSteel;
	public static Item				knifeDiamond;
	public static Item				knifeGold;
	
	public static Item				bayonetWood;
	public static Item				bayonetStone;
	public static Item				bayonetSteel;
	public static Item				bayonetDiamond;
	public static Item				bayonetGold;
	public static Item				musketBullet;
	public static Item				musket;
	public static Item				gunStock;
	public static Item				musket_iron_part;
	
	public static Item				battleaxeWood;
	public static Item				battleaxeStone;
	public static Item				battleaxeSteel;
	public static Item				battleaxeDiamond;
	public static Item				battleaxeGold;
	
	public static Item				warhammerWood, warhammerStone, warhammerSteel, warhammerDiamond, warhammerGold;
	
	public static Item				crossbow;
	public static Item				bolt;
	
	public static Item				blowgun;
	public static Item				dart;
	public static Item				dynamite;
	
	public static Item				flailWood, flailStone, flailSteel, flailDiamond, flailGold;
	
	public static Item				fireRod;
	
	public static Item				cannon;
	public static Item				cannonBall;
	
	public static Item				blunderShot;
	public static Item				blunderbuss;
	public static Item				blunder_iron_part;
	
	public static Item				dummy;
	
	public static Item				boomerangWood, boomerangStone, boomerangSteel, boomerangDiamond, boomerangGold;
	
	public static Item				katanaWood, katanaStone, katanaSteel, katanaDiamond, katanaGold;
	
	public static Item				flintlockPistol;
	
	public WeaponModConfig			modConfig;
	public WMMessagePipeline		messagePipeline;
	
	public BalkonsWeaponMod()
	{
		messagePipeline = new WMMessagePipeline();
	}
	
	@EventHandler
	public void preInitMod(FMLPreInitializationEvent event)
	{
		modLog = event.getModLog();
		
		modConfig = new WeaponModConfig(new Configuration(event.getSuggestedConfigurationFile()));
		modConfig.addEnableSetting("spear");
		modConfig.addEnableSetting("halberd");
		modConfig.addEnableSetting("battleaxe");
		modConfig.addEnableSetting("knife");
		modConfig.addEnableSetting("warhammer");
		modConfig.addEnableSetting("flail");
		modConfig.addEnableSetting("katana");
		modConfig.addEnableSetting("boomerang");
		modConfig.addEnableSetting("firerod");
		modConfig.addEnableSetting("javelin");
		modConfig.addEnableSetting("crossbow");
		modConfig.addEnableSetting("blowgun");
		modConfig.addEnableSetting("musket");
		modConfig.addEnableSetting("blunderbuss");
		modConfig.addEnableSetting("flintlock");
		modConfig.addEnableSetting("dynamite");
		modConfig.addEnableSetting("cannon");
		modConfig.addEnableSetting("dummy");
		
		modConfig.addReloadTimeSetting("musket", 30);
		modConfig.addReloadTimeSetting("crossbow", 15);
		modConfig.addReloadTimeSetting("blowgun", 10);
		modConfig.addReloadTimeSetting("blunderbuss", 20);
		modConfig.addReloadTimeSetting("flintlock", 15);
		modConfig.loadConfig();
		
		addModItems();
	}
	
	@EventHandler
	public void initMod(FMLInitializationEvent event)
	{
		messagePipeline.initalize();
		proxy.registerPackets(messagePipeline);
		proxy.registerEventHandlers();
		proxy.registerIcons();
		proxy.registerRenderers(modConfig);
		
		registerWeapons();
		registerDispenseBehavior();
	}
	
	@EventHandler
	public void postInitMod(FMLPostInitializationEvent event)
	{
		messagePipeline.postInitialize();
	}
	
	private void addModItems()
	{
		if (modConfig.isEnabled("spear"))
		{
			spearWood = new ItemMelee("spear.wood", new MeleeCompSpear(Item.ToolMaterial.WOOD));
			spearStone = new ItemMelee("spear.stone", new MeleeCompSpear(Item.ToolMaterial.STONE));
			spearSteel = new ItemMelee("spear.iron", new MeleeCompSpear(Item.ToolMaterial.IRON));
			spearGold = new ItemMelee("spear.gold", new MeleeCompSpear(Item.ToolMaterial.GOLD));
			spearDiamond = new ItemMelee("spear.diamond", new MeleeCompSpear(Item.ToolMaterial.EMERALD));
		}
		
		if (modConfig.isEnabled("halberd"))
		{
			halberdWood = new ItemMelee("halberd.wood", new MeleeCompHalberd(Item.ToolMaterial.WOOD));
			halberdStone = new ItemMelee("halberd.stone", new MeleeCompHalberd(Item.ToolMaterial.STONE));
			halberdSteel = new ItemMelee("halberd.iron", new MeleeCompHalberd(Item.ToolMaterial.IRON));
			halberdGold = new ItemMelee("halberd.gold", new MeleeCompHalberd(Item.ToolMaterial.GOLD));
			halberdDiamond = new ItemMelee("halberd.diamond", new MeleeCompHalberd(Item.ToolMaterial.EMERALD));
		}
		
		if (modConfig.isEnabled("battleaxe"))
		{
			battleaxeWood = new ItemMelee("battleaxe.wood", new MeleeCompBattleaxe(Item.ToolMaterial.WOOD));
			battleaxeStone = new ItemMelee("battleaxe.stone", new MeleeCompBattleaxe(Item.ToolMaterial.STONE));
			battleaxeSteel = new ItemMelee("battleaxe.iron", new MeleeCompBattleaxe(Item.ToolMaterial.IRON));
			battleaxeGold = new ItemMelee("battleaxe.gold", new MeleeCompBattleaxe(Item.ToolMaterial.GOLD));
			battleaxeDiamond = new ItemMelee("battleaxe.diamond", new MeleeCompBattleaxe(Item.ToolMaterial.EMERALD));
		}
		
		if (modConfig.isEnabled("knife"))
		{
			knifeWood = new ItemMelee("knife.wood", new MeleeCompKnife(Item.ToolMaterial.WOOD));
			knifeStone = new ItemMelee("knife.stone", new MeleeCompKnife(Item.ToolMaterial.STONE));
			knifeSteel = new ItemMelee("knife.iron", new MeleeCompKnife(Item.ToolMaterial.IRON));
			knifeGold = new ItemMelee("knife.gold", new MeleeCompKnife(Item.ToolMaterial.GOLD));
			knifeDiamond = new ItemMelee("knife.diamond", new MeleeCompKnife(Item.ToolMaterial.EMERALD));
		}
		
		if (modConfig.isEnabled("warhammer"))
		{
			warhammerWood = new ItemMelee("warhammer.wood", new MeleeCompWarhammer(Item.ToolMaterial.WOOD));
			warhammerStone = new ItemMelee("warhammer.stone", new MeleeCompWarhammer(Item.ToolMaterial.STONE));
			warhammerSteel = new ItemMelee("warhammer.iron", new MeleeCompWarhammer(Item.ToolMaterial.IRON));
			warhammerGold = new ItemMelee("warhammer.gold", new MeleeCompWarhammer(Item.ToolMaterial.GOLD));
			warhammerDiamond = new ItemMelee("warhammer.diamond", new MeleeCompWarhammer(Item.ToolMaterial.EMERALD));
		}
		
		if (modConfig.isEnabled("flail"))
		{
			flailWood = new ItemFlail("flail.wood", Item.ToolMaterial.WOOD);
			flailStone = new ItemFlail("flail.stone", Item.ToolMaterial.STONE);
			flailSteel = new ItemFlail("flail.iron", Item.ToolMaterial.IRON);
			flailGold = new ItemFlail("flail.gold", Item.ToolMaterial.GOLD);
			flailDiamond = new ItemFlail("flail.diamond", Item.ToolMaterial.EMERALD);
		}
		
		if (modConfig.isEnabled("katana"))
		{
			katanaWood = new ItemMelee("katana.wood", new MeleeComponent(MeleeSpecs.KATANA, Item.ToolMaterial.WOOD));
			katanaStone = new ItemMelee("katana.stone", new MeleeComponent(MeleeSpecs.KATANA, Item.ToolMaterial.STONE));
			katanaSteel = new ItemMelee("katana.iron", new MeleeComponent(MeleeSpecs.KATANA, Item.ToolMaterial.IRON));
			katanaGold = new ItemMelee("katana.gold", new MeleeComponent(MeleeSpecs.KATANA, Item.ToolMaterial.GOLD));
			katanaDiamond = new ItemMelee("katana.diamond", new MeleeComponent(MeleeSpecs.KATANA, Item.ToolMaterial.EMERALD));
		}
		
		if (modConfig.isEnabled("boomerang"))
		{
			boomerangWood = new ItemMelee("boomerang.wood", new MeleeCompBoomerang(Item.ToolMaterial.WOOD));
			boomerangStone = new ItemMelee("boomerang.stone", new MeleeCompBoomerang(Item.ToolMaterial.STONE));
			boomerangSteel = new ItemMelee("boomerang.iron", new MeleeCompBoomerang(Item.ToolMaterial.IRON));
			boomerangGold = new ItemMelee("boomerang.gold", new MeleeCompBoomerang(Item.ToolMaterial.GOLD));
			boomerangDiamond = new ItemMelee("boomerang.diamond", new MeleeCompBoomerang(Item.ToolMaterial.EMERALD));
		}
		
		if (modConfig.isEnabled("firerod"))
		{
			fireRod = new ItemMelee("firerod", new MeleeCompFirerod());
		}
		
		if (modConfig.isEnabled("javelin"))
		{
			javelin = new ItemJavelin("javelin");
		}
		
		if (modConfig.isEnabled("crossbow"))
		{
			crossbow = new ItemCrossbow("crossbow", new RangedCompCrossbow(), new MeleeCompNone());
			bolt = new WMItem("bolt");
		}
		
		if (modConfig.isEnabled("blowgun"))
		{
			blowgun = new ItemShooter("blowgun", new RangedCompBlowgun(), new MeleeCompNone());
			dart = new ItemBlowgunDart("dart");
		}
		
		if (modConfig.isEnabled("musket"))
		{
			if (modConfig.isEnabled("knife"))
			{
				bayonetWood = new ItemMusket("musketbayonet.wood", new MeleeCompKnife(Item.ToolMaterial.WOOD), knifeWood);
				bayonetStone = new ItemMusket("musketbayonet.stone", new MeleeCompKnife(Item.ToolMaterial.STONE), knifeStone);
				bayonetSteel = new ItemMusket("musketbayonet.iron", new MeleeCompKnife(Item.ToolMaterial.IRON), knifeSteel);
				bayonetGold = new ItemMusket("musketbayonet.gold", new MeleeCompKnife(Item.ToolMaterial.GOLD), knifeGold);
				bayonetDiamond = new ItemMusket("musketbayonet.diamond", new MeleeCompKnife(Item.ToolMaterial.EMERALD), knifeDiamond);
			}
			
			musket = new ItemMusket("musket", new MeleeCompNone(), null);
			musket_iron_part = new WMItem("musket-ironpart");
		}
		
		if (modConfig.isEnabled("blunderbuss"))
		{
			blunderbuss = new ItemShooter("blunderbuss", new RangedCompBlunderbuss(), new MeleeCompNone());
			blunder_iron_part = new WMItem("blunder-ironpart");
			blunderShot = new WMItem("shot");
		}
		
		if (modConfig.isEnabled("flintlock"))
		{
			flintlockPistol = new ItemShooter("flintlock", new RangedCompFlintlock(), new MeleeCompNone());
		}
		
		if (modConfig.isEnabled("dynamite"))
		{
			dynamite = new ItemDynamite("dynamite");
		}
		
		if (modConfig.isEnabled("cannon"))
		{
			cannon = new ItemCannon("cannon");
			cannonBall = new WMItem("cannonball");
		}
		
		if (modConfig.isEnabled("dummy"))
		{
			dummy = new ItemDummy("dummy");
		}
		
		if (modConfig.isEnabled("musket") || modConfig.isEnabled("blunderbuss"))
		{
			gunStock = new WMItem("gun-stock");
		}
		
		if (modConfig.isEnabled("musket") || modConfig.isEnabled("flintlock"))
		{
			musketBullet = new WMItem("bullet");
		}
	}
	
	private void registerWeapons()
	{
		if (modConfig.isEnabled("spear"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(spearWood, "  #", " X ", "X  ", 'X', "stickWood", '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(spearStone, "  #", " X ", "X  ", 'X', "stickWood", '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(spearSteel, "  #", " X ", "X  ", 'X', "stickWood", '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(spearDiamond, "  #", " X ", "X  ", 'X', "stickWood", '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(spearGold, "  #", " X ", "X  ", 'X', "stickWood", '#', "ingotGold"));
			
			EntityRegistry.registerModEntity(EntitySpear.class, "spear", 1, this, 64, 20, true);
		}
		
		if (modConfig.isEnabled("halberd"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(halberdWood, " ##", " X#", "X  ", 'X', "stickWood", '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(halberdStone, " ##", " X#", "X  ", 'X', "stickWood", '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(halberdSteel, " ##", " X#", "X  ", 'X', "stickWood", '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(halberdDiamond, " ##", " X#", "X  ", 'X', "stickWood", '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(halberdGold, " ##", " X#", "X  ", 'X', "stickWood", '#', "ingotGold"));
		}
		
		if (modConfig.isEnabled("knife"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeWood, "#X", 'X', "stickWood", '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeStone, "#X", 'X', "stickWood", '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeSteel, "#X", 'X', "stickWood", '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeDiamond, "#X", 'X', "stickWood", '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeGold, "#X", 'X', "stickWood", '#', "ingotGold"));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeWood, "#", "X", 'X', "stickWood", '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeStone, "#", "X", 'X', "stickWood", '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeSteel, "#", "X", 'X', "stickWood", '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeDiamond, "#", "X", 'X', "stickWood", '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(knifeGold, "#", "X", 'X', "stickWood", '#', "ingotGold"));
			
			EntityRegistry.registerModEntity(EntityKnife.class, "knife", 2, this, 64, 20, true);
		}
		
		if (modConfig.isEnabled("javelin"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(javelin, 4), "  #", " X ", "X  ", 'X', "stickWood", '#', Items.flint));
			
			EntityRegistry.registerModEntity(EntityJavelin.class, "javelin", 3, this, 64, 20, true);
		}
		
		if (modConfig.isEnabled("musket"))
		{
			if (modConfig.isEnabled("knife"))
			{
				GameRegistry.addShapelessRecipe(new ItemStack(bayonetWood), knifeWood, musket);
				GameRegistry.addShapelessRecipe(new ItemStack(bayonetStone), knifeStone, musket);
				GameRegistry.addShapelessRecipe(new ItemStack(bayonetSteel), knifeSteel, musket);
				GameRegistry.addShapelessRecipe(new ItemStack(bayonetDiamond), knifeDiamond, musket);
				GameRegistry.addShapelessRecipe(new ItemStack(bayonetGold), knifeGold, musket);
			}
			
			GameRegistry.addRecipe(new ItemStack(musket), "#", "X", 'X', gunStock, '#', musket_iron_part);
			GameRegistry.addRecipe(new ShapedOreRecipe(musket_iron_part, "XX#", "  X", 'X', "ingotIron", '#', Items.flint_and_steel));
		}
		
		if (modConfig.isEnabled("musket") || modConfig.isEnabled("flintlock"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(musketBullet, 8), "X", "#", "O", 'X', "ingotIron", '#', Items.gunpowder, 'O', Items.paper));
			
			EntityRegistry.registerModEntity(EntityMusketBullet.class, "bullet", 4, this, 16, 20, true);
		}
		
		if (modConfig.isEnabled("battleaxe"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeWood, "###", "#X#", " X ", 'X', "stickWood", '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeStone, "###", "#X#", " X ", 'X', "stickWood", '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeSteel, "###", "#X#", " X ", 'X', "stickWood", '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeDiamond, "###", "#X#", " X ", 'X', "stickWood", '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeGold, "###", "#X#", " X ", 'X', "stickWood", '#', "ingotGold"));
		}
		
		if (modConfig.isEnabled("warhammer"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(warhammerWood, "#X#", "#X#", " X ", 'X', "stickWood", '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(warhammerStone, "#X#", "#X#", " X ", 'X', "stickWood", '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(warhammerSteel, "#X#", "#X#", " X ", 'X', "stickWood", '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(warhammerDiamond, "#X#", "#X#", " X ", 'X', "stickWood", '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(warhammerGold, "#X#", "#X#", " X ", 'X', "stickWood", '#', "ingotGold"));
		}
		
		if (modConfig.isEnabled("crossbow"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(crossbow, "O##", "#X ", "# X", 'X', "plankWood", '#', "ingotIron", 'O', Items.bow));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bolt, 4), "#", "X", 'X', Items.feather, '#', "ingotIron"));
			
			EntityRegistry.registerModEntity(EntityCrossbowBolt.class, "bolt", 5, this, 64, 20, true);
		}
		
		if (modConfig.isEnabled("blowgun"))
		{
			GameRegistry.addRecipe(new ItemStack(blowgun), "X  ", " X ", "  X", 'X', Items.reeds);
			for (DartType type : DartType.dartTypes)
			{
				if (type != null)
				{
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dart, 4, type.typeID), "#", "X", "O", 'X', type.craftItem, '#', "stickWood", 'O', Items.feather));
				}
			}
			
			EntityRegistry.registerModEntity(EntityBlowgunDart.class, "dart", 6, this, 64, 20, true);
		}
		
		if (modConfig.isEnabled("dynamite"))
		{
			GameRegistry.addRecipe(new ItemStack(dynamite, 2), "#", "X", "X", 'X', Items.gunpowder, '#', Items.string);
			
			EntityRegistry.registerModEntity(EntityDynamite.class, "dynamite", 7, this, 64, 20, true);
		}
		
		if (modConfig.isEnabled("flail"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(flailWood, "  O", " XO", "X #", 'X', "stickWood", 'O', Items.string, '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(flailStone, "  O", " XO", "X #", 'X', "stickWood", 'O', Items.string, '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(flailSteel, "  O", " XO", "X #", 'X', "stickWood", 'O', Items.string, '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(flailDiamond, "  O", " XO", "X #", 'X', "stickWood", 'O', Items.string, '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(flailGold, "  O", " XO", "X #", 'X', "stickWood", 'O', Items.string, '#', "ingotGold"));
			
			EntityRegistry.registerModEntity(EntityFlail.class, "flail", 8, this, 32, 20, true);
		}
		
		if (modConfig.isEnabled("firerod"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(fireRod, "#  ", " X ", "  X", 'X', "stickWood", '#', Blocks.torch));
		}
		
		if (modConfig.isEnabled("cannon"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(cannon, "XX#", "  X", "XXO", 'X', "ingotIron", '#', Items.flint_and_steel, 'O', "logWood"));
			EntityRegistry.registerModEntity(EntityCannon.class, "cannon", 9, this, 64, 128, false);
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cannonBall, 4), " X ", "XXX", " X ", 'X', "stone"));
			EntityRegistry.registerModEntity(EntityCannonBall.class, "cannonball", 10, this, 64, 20, true);
		}
		
		if (modConfig.isEnabled("blunderbuss"))
		{
			GameRegistry.addRecipe(new ItemStack(blunderShot, 8), "X", "#", "O", 'X', Blocks.gravel, '#', Items.gunpowder, 'O', Items.paper);
			GameRegistry.addRecipe(new ItemStack(blunderbuss), "#", "X", 'X', gunStock, '#', blunder_iron_part);
			GameRegistry.addRecipe(new ShapedOreRecipe(blunder_iron_part, "X  ", " X#", "X X", 'X', "ingotIron", '#', Items.flint_and_steel));
			
			EntityRegistry.registerModEntity(EntityBlunderShot.class, "shot", 11, this, 16, 20, true);
		}
		
		if (modConfig.isEnabled("musket") || modConfig.isEnabled("blunderbuss"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(gunStock, "XX#", 'X', "stickWood", '#', "plankWood"));
		}
		
		if (modConfig.isEnabled("dummy"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(dummy, " U ", "XOX", " # ", '#', "stickWood", 'X', "cropWheat", 'O', Items.leather_chestplate, 'U', Blocks.wool));
			
			EntityRegistry.registerModEntity(EntityDummy.class, "dummy", 12, this, 64, 20, false);
		}
		
		if (modConfig.isEnabled("boomerang"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(boomerangWood, "XX#", "  X", "  X", 'X', "plankWood", '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(boomerangStone, "XX#", "  X", "  X", 'X', "plankWood", '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(boomerangSteel, "XX#", "  X", "  X", 'X', "plankWood", '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(boomerangDiamond, "XX#", "  X", "  X", 'X', "plankWood", '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(boomerangGold, "XX#", "  X", "  X", 'X', "plankWood", '#', "ingotGold"));
			
			EntityRegistry.registerModEntity(EntityBoomerang.class, "boomerang", 13, this, 64, 20, true);
		}
		
		if (modConfig.isEnabled("katana"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(katanaWood, "  #", " # ", "X  ", 'X', "stickWood", '#', "plankWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(katanaStone, "  #", " # ", "X  ", 'X', "stickWood", '#', Blocks.cobblestone));
			GameRegistry.addRecipe(new ShapedOreRecipe(katanaSteel, "  #", " # ", "X  ", 'X', "stickWood", '#', "ingotIron"));
			GameRegistry.addRecipe(new ShapedOreRecipe(katanaDiamond, "  #", " # ", "X  ", 'X', "stickWood", '#', "gemDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(katanaGold, "  #", " # ", "X  ", 'X', "stickWood", '#', "ingotGold"));
		}
		
		if (modConfig.isEnabled("flintlock"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(flintlockPistol, "XX#", " -O", 'X', "ingotIron", '#', Items.flint_and_steel, '-', "stickWood", 'O', "plankWood"));
		}
	}
	
	private void registerDispenseBehavior()
	{
		if (musketBullet != null)
		{
			BlockDispenser.dispenseBehaviorRegistry.putObject(musketBullet, new DispenseMusketBullet());
		}
		if (javelin != null)
		{
			BlockDispenser.dispenseBehaviorRegistry.putObject(javelin, new DispenseJavelin());
		}
		if (bolt != null)
		{
			BlockDispenser.dispenseBehaviorRegistry.putObject(bolt, new DispenseCrossbowBolt());
		}
		if (dart != null)
		{
			BlockDispenser.dispenseBehaviorRegistry.putObject(dart, new DispenseBlowgunDart());
		}
		if (dynamite != null)
		{
			BlockDispenser.dispenseBehaviorRegistry.putObject(dynamite, new DispenseDynamite());
		}
		if (blunderShot != null)
		{
			BlockDispenser.dispenseBehaviorRegistry.putObject(blunderShot, new DispenseBlunderShot());
		}
		if (modConfig.isEnabled("cannon"))
		{
			DispenseCannonBall behavior = new DispenseCannonBall();
			BlockDispenser.dispenseBehaviorRegistry.putObject(cannonBall, behavior);
			BlockDispenser.dispenseBehaviorRegistry.putObject(Items.gunpowder, behavior);
		}
	}
	
	/*
	@Override
	public int dispense(int x, int y, int z, int xVel, int zVel, World world, ItemStack item, Random random, double entX, double entY, double entZ)
	{
		if (musketBullet != null && item.itemID == musketBullet.shiftedIndex)
		{
			EntityMusketBullet entitymusketbullet = new EntityMusketBullet(world, x, y, z);

			entitymusketbullet.setThrowableHeading(xVel, 0.0D, zVel, 5.0F, 3.0F);
			world.spawnEntityInWorld(entitymusketbullet);
			world.playSoundEffect(x, y, z, "random.explode", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.7F));
			world.playSoundEffect(x, y, z, "ambient.weather.thunder", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.4F));
			world.spawnParticle("flame", x + xVel, y, z + zVel, 0.0D, 0.0D, 0.0D);

		} else if (javelin != null && item.itemID == javelin.shiftedIndex)
		{
			EntityJavelin entityjavelin = new EntityJavelin(world, x, y, z);
			entityjavelin.setPickupMode(EntityProjectile.PICKUP_ALL);
			entityjavelin.setThrowableHeading(xVel, 0.1D, zVel, 1.1F, 4.0F);
			world.spawnEntityInWorld(entityjavelin);
			world.playSoundEffect(x, y, z, "random.bow", 1.0F, 1.2F);

		} else if (bolt != null && item.itemID == bolt.shiftedIndex)
		{
			EntityCrossbowBolt entitycrossbowbolt = new EntityCrossbowBolt(world, x, y, z);
			entitycrossbowbolt.setPickupMode(EntityProjectile.PICKUP_ALL);
			entitycrossbowbolt.setThrowableHeading(xVel, 0.0D, zVel, 5.0F, 4.0F);
			world.spawnEntityInWorld(entitycrossbowbolt);
			world.playSoundEffect(x, y, z, "random.bow", 1.0F, 1.2F);

		} else if (dart != null && item.itemID == dart.shiftedIndex)
		{
			EntityBlowgunDart entityblowgundart = new EntityBlowgunDart(world, x, y, z);
			entityblowgundart.setPickupMode(EntityProjectile.PICKUP_ALL);
			entityblowgundart.setThrowableHeading(xVel, 0.0D, zVel, 5.0F, 4.0F);
			world.spawnEntityInWorld(entityblowgundart);
			world.playSoundEffect(x, y, z, "random.bow", 1.0F, 1.2F);

		} else if (dynamite != null && item.itemID == dynamite.shiftedIndex)
		{
			EntityDynamite entitydynamite = new EntityDynamite(world, x, y, z);
			entitydynamite.setThrowableHeading(xVel, 0.1D, zVel, 1.0F, 4.0F);
			world.spawnEntityInWorld(entitydynamite);
			world.playSoundEffect(x, y, z, "random.fuse", 1.0F, 1.2F);

		} else if (blunderShot != null && item.itemID == blunderShot.shiftedIndex)
		{
			EntityBlunderShot.fireFromDispenser(world, x, y, z, xVel, zVel);
			world.playSoundEffect(x, y, z, "random.old_explode", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 1.0F));
			world.spawnParticle("flame", x + xVel, y, z + zVel, 0.0D, 0.0D, 0.0D);

		} else if (WeaponRegistry.isEnabled("cannon") && (item.itemID == cannonBall.shiftedIndex || item.itemID == Items.gunpowder.shiftedIndex))
		{
			boolean canfire = false;
			TileEntity tileentity = world.getBlockTileEntity(MathHelper.floor_double(x - xVel), MathHelper.floor_double(y), MathHelper.floor_double(z - zVel));
			if (tileentity instanceof TileEntityDispenser)
			{
				TileEntityDispenser dispenser = ((TileEntityDispenser) tileentity);
				int itemtocheck = 0;
				if (item.itemID == Items.gunpowder.shiftedIndex)
				{
					itemtocheck = cannonBall.shiftedIndex;
				} else if (item.itemID == cannonBall.shiftedIndex)
				{
					itemtocheck = Items.gunpowder.shiftedIndex;
				}

				int k;
				for (k = 0; k < dispenser.getSizeInventory(); k++)
				{
					ItemStack itemstack1 = dispenser.getStackInSlot(k);
					if (itemstack1 != null && itemstack1.itemID == itemtocheck)
					{
						dispenser.decrStackSize(k, 1);
						canfire = true;
						break;
					}
				}
			}

			if (!canfire) return 0;

			EntityCannonBall entitycannonball = new EntityCannonBall(world, x, y, z);
			entitycannonball.setThrowableHeading(xVel, 0.1D, zVel, 2.0F, 2.0F);
			world.spawnEntityInWorld(entitycannonball);
			world.playSoundEffect(x, y, z, "random.explode", 8.0F, 1.0F / (rand.nextFloat() * 0.8F + 0.9F));
			world.playSoundEffect(x, y, z, "ambient.weather.thunder", 8.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.6F));
			world.spawnParticle("flame", x + xVel, y, z + zVel, 0.0D, 0.0D, 0.0D);
		} else
			return 0;
		return -1;
	}
	 */
}
