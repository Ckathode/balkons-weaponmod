package ckathode.weaponmod;

import ckathode.weaponmod.item.ItemBlowgunDart;
import ckathode.weaponmod.item.WMItem;
import ckathode.weaponmod.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
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
import ckathode.weaponmod.network.WMMessagePipeline;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class WMClientProxy extends WMCommonProxy
{
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
		WMClientEventHandler eventhandler = new WMClientEventHandler();
		FMLCommonHandler.instance().bus().register(eventhandler);
		MinecraftForge.EVENT_BUS.register(eventhandler);

	}

	@Override
	public void registerPackets(WMMessagePipeline pipeline)
	{
		super.registerPackets(pipeline);
	}

	@Override
	public void registerIcons()
	{
	}

	@Override
	public void registerRenderers(WeaponModConfig config)
	{
		ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        LongItemRenderer longrender = new LongItemRenderer();

        if (config.isEnabled("spear")){
            modelMesher.register(BalkonsWeaponMod.spearWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.spearWood.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.spearStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.spearStone.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.spearSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.spearSteel.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.spearDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.spearDiamond.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.spearGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.spearGold.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderSpear(renderManager));

            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearWood, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearStone, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearSteel, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearDiamond, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearGold, longrender);
        }
        if (config.isEnabled("halberd")) {
            modelMesher.register(BalkonsWeaponMod.halberdWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.halberdWood.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.halberdStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.halberdStone.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.halberdSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.halberdSteel.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.halberdDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.halberdDiamond.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.halberdGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.halberdGold.getUnlocalizedName().substring(5), "inventory"));

            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdWood, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdStone, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdSteel, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdDiamond, longrender);
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdGold, longrender);
        }
        if (config.isEnabled("battleaxe")) {
            modelMesher.register(BalkonsWeaponMod.battleaxeWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.battleaxeWood.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.battleaxeStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.battleaxeStone.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.battleaxeSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.battleaxeSteel.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.battleaxeDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.battleaxeDiamond.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.battleaxeGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.battleaxeGold.getUnlocalizedName().substring(5), "inventory"));
        }
        if (config.isEnabled("knife")){
            modelMesher.register(BalkonsWeaponMod.knifeWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.knifeWood.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.knifeStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.knifeStone.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.knifeSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.knifeSteel.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.knifeDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.knifeDiamond.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.knifeGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.knifeGold.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderKnife(renderManager));
        }
        if (config.isEnabled("warhammer")) {
            modelMesher.register(BalkonsWeaponMod.warhammerWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.warhammerWood.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.warhammerStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.warhammerStone.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.warhammerSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.warhammerSteel.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.warhammerDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.warhammerDiamond.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.warhammerGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.warhammerGold.getUnlocalizedName().substring(5), "inventory"));
        }
        if (config.isEnabled("flail"))
        {
            modelMesher.register(BalkonsWeaponMod.flailWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.flailWood.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.flailStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.flailStone.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.flailSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.flailSteel.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.flailDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.flailDiamond.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.flailGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.flailGold.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityFlail.class, new RenderFlail(renderManager));
        }
		if (config.isEnabled("katana")) {
			modelMesher.register(BalkonsWeaponMod.katanaWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.katanaWood.getUnlocalizedName().substring(5), "inventory"));
			modelMesher.register(BalkonsWeaponMod.katanaStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.katanaStone.getUnlocalizedName().substring(5), "inventory"));
			modelMesher.register(BalkonsWeaponMod.katanaSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.katanaSteel.getUnlocalizedName().substring(5), "inventory"));
			modelMesher.register(BalkonsWeaponMod.katanaDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.katanaDiamond.getUnlocalizedName().substring(5), "inventory"));
			modelMesher.register(BalkonsWeaponMod.katanaGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.katanaGold.getUnlocalizedName().substring(5), "inventory"));
		}
        if (config.isEnabled("boomerang"))
        {
            modelMesher.register(BalkonsWeaponMod.boomerangWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.boomerangWood.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.boomerangStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.boomerangStone.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.boomerangSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.boomerangSteel.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.boomerangDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.boomerangDiamond.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.boomerangGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.boomerangGold.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, new RenderBoomerang(renderManager));
        }
        if (config.isEnabled("firerood")){
            modelMesher.register(BalkonsWeaponMod.fireRod, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.fireRod.getUnlocalizedName().substring(5), "inventory"));
        }
		if (config.isEnabled("javelin")){
			modelMesher.register(BalkonsWeaponMod.javelin, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.javelin.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, new RenderJavelin(renderManager));
            MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.javelin, longrender);
		}
        if (config.isEnabled("crossbow"))
        {
            modelMesher.register(BalkonsWeaponMod.crossbow, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.crossbow.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.bolt, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.bolt.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, new RenderCrossbowBolt(renderManager));
        }
        if (config.isEnabled("blowgun"))
        {
            modelMesher.register(BalkonsWeaponMod.blowgun, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.blowgun.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.dart, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + ((ItemBlowgunDart) BalkonsWeaponMod.dart).getUnlocalizedName(0).substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.dart, 1, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + ((ItemBlowgunDart) BalkonsWeaponMod.dart).getUnlocalizedName(1).substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.dart, 2, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + ((ItemBlowgunDart) BalkonsWeaponMod.dart).getUnlocalizedName(2).substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.dart, 3, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + ((ItemBlowgunDart) BalkonsWeaponMod.dart).getUnlocalizedName(3).substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityBlowgunDart.class, new RenderBlowgunDart(renderManager));
        }
		if (config.isEnabled("musket"))
		{
            if (config.isEnabled("knife"))
            {
                modelMesher.register(BalkonsWeaponMod.bayonetWood, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.bayonetWood.getUnlocalizedName().substring(5), "inventory"));
                modelMesher.register(BalkonsWeaponMod.bayonetStone, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.bayonetStone.getUnlocalizedName().substring(5), "inventory"));
                modelMesher.register(BalkonsWeaponMod.bayonetSteel, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.bayonetSteel.getUnlocalizedName().substring(5), "inventory"));
                modelMesher.register(BalkonsWeaponMod.bayonetGold, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.bayonetGold.getUnlocalizedName().substring(5), "inventory"));
                modelMesher.register(BalkonsWeaponMod.bayonetDiamond, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.bayonetDiamond.getUnlocalizedName().substring(5), "inventory"));
            }

            modelMesher.register(BalkonsWeaponMod.musket, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.musket.getUnlocalizedName().substring(5), "inventory"));
			modelMesher.register(BalkonsWeaponMod.musket_iron_part, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.musket_iron_part.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityMusketBullet.class, new RenderMusketBullet(renderManager));
		}
        if (config.isEnabled("blunderbuss"))
        {
            modelMesher.register(BalkonsWeaponMod.blunderbuss, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.blunderbuss.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.blunder_iron_part, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.blunder_iron_part.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.blunderShot, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.blunderShot.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityBlunderShot.class, new RenderBlunderShot(renderManager));
        }
        if (config.isEnabled("flintlock"))
        {
            modelMesher.register(BalkonsWeaponMod.flintlockPistol, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.flintlockPistol.getUnlocalizedName().substring(5), "inventory"));
        }
        if (config.isEnabled("dynamite"))
        {
            modelMesher.register(BalkonsWeaponMod.dynamite, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.dynamite.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new RenderDynamite(renderManager));
        }
        if (config.isEnabled("cannon"))
        {
            modelMesher.register(BalkonsWeaponMod.cannon, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.cannon.getUnlocalizedName().substring(5), "inventory"));
            modelMesher.register(BalkonsWeaponMod.cannonBall, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.cannonBall.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityCannon.class, new RenderCannon(renderManager));
            RenderingRegistry.registerEntityRenderingHandler(EntityCannonBall.class, new RenderCannonBall(renderManager));
        }
        if (config.isEnabled("dummy"))
        {
            modelMesher.register(BalkonsWeaponMod.dummy, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.dummy.getUnlocalizedName().substring(5), "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, new RenderDummy(renderManager));
        }
		if (config.isEnabled("musket") || config.isEnabled("blunderbuss"))
		{
			modelMesher.register(BalkonsWeaponMod.gunStock, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.gunStock.getUnlocalizedName().substring(5), "inventory"));
		}
        if (config.isEnabled("musket") || config.isEnabled("flintlock"))
        {
            modelMesher.register(BalkonsWeaponMod.musketBullet, 0, new ModelResourceLocation(BalkonsWeaponMod.MOD_ID + ":" + BalkonsWeaponMod.musketBullet.getUnlocalizedName().substring(5), "inventory"));
        }
		if (config.isEnabled("firerod"))
		{
			//MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.fireRod.itemID, stabrender);
		}
	}
}
