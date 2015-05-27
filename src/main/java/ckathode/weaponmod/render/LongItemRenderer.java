package ckathode.weaponmod.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LongItemRenderer extends WeaponItemRenderer
{
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		Tessellator tess = Tessellator.getInstance();
		
		EntityLivingBase entityliving = (EntityLivingBase) data[1];
		
		GL11.glTranslatef(-0.5F, -0.5F, 0F);
		GL11.glScalef(2F, 2F, 1.4F);
		
		float t = 0.0625F;
		renderEnchantEffect(tess, item, 256, 256, t);
	}
}
