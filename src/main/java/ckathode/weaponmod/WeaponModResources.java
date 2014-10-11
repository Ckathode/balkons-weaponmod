package ckathode.weaponmod;

import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class WeaponModResources
{
	public static abstract class Textures
	{
		public static final ResourceLocation	DART				= new ResourceLocation("weaponmod", "textures/entity/blowgun_dart.png");
		public static final ResourceLocation	BOOMERANG			= new ResourceLocation("weaponmod", "textures/entity/boomerang.png");
		public static final ResourceLocation	CANNON				= new ResourceLocation("weaponmod", "textures/entity/cannon.png");
		public static final ResourceLocation	CANNONBALL			= new ResourceLocation("weaponmod", "textures/entity/cannon_ball.png");
		public static final ResourceLocation	BOLT				= new ResourceLocation("weaponmod", "textures/entity/crossbow_bolt.png");
		public static final ResourceLocation	DUMMY				= new ResourceLocation("weaponmod", "textures/entity/dummy.png");
		public static final ResourceLocation	DYNAMITE			= new ResourceLocation("weaponmod", "textures/entity/dynamite.png");
		public static final ResourceLocation	FLAIL				= new ResourceLocation("weaponmod", "textures/entity/flail.png");
		public static final ResourceLocation	JAVELIN				= new ResourceLocation("weaponmod", "textures/entity/spear.png");
		public static final ResourceLocation	KNIFE				= new ResourceLocation("weaponmod", "textures/entity/knife.png");
		public static final ResourceLocation	BULLET				= new ResourceLocation("weaponmod", "textures/entity/musket_bullet.png");
		public static final ResourceLocation	SPEAR				= new ResourceLocation("weaponmod", "textures/entity/spear.png");
		public static final ResourceLocation	ENCHANTMENT_GLINT	= new ResourceLocation("minecraft", "%blur%/misc/enchanted_item_glint.png");
		public static final ResourceLocation	ICONS				= new ResourceLocation("weaponmod", "textures/gui/icons.png");
	}
}
