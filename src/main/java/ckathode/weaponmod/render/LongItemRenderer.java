package ckathode.weaponmod.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LongItemRenderer extends WeaponItemRenderer
{
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		Tessellator tess = Tessellator.instance;
		
		EntityLivingBase entityliving = (EntityLivingBase) data[1];
		IIcon icon = entityliving.getItemIcon(item, 0);
		
		GL11.glTranslatef(-0.5F, -0.5F, 0F);
		GL11.glScalef(2F, 2F, 1.4F);
		
		float t = 0.0625F;
		renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth() * 16, icon.getIconHeight() * 16, t);
		renderEnchantEffect(tess, item, 256, 256, t);
	}
}
