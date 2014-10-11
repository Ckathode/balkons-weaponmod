package ckathode.weaponmod.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import ckathode.weaponmod.WeaponModResources;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WeaponItemRenderer implements IItemRenderer
{
	protected Minecraft	mc;
	
	public WeaponItemRenderer()
	{
		mc = FMLClientHandler.instance().getClient();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type == ItemRenderType.EQUIPPED;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		Tessellator tess = Tessellator.instance;
		
		EntityLivingBase entityliving = (EntityLivingBase) data[1];
		IIcon icon = entityliving.getItemIcon(item, 0);
		
		float t = 0.0625F;
		renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), t);
		renderEnchantEffect(tess, item, 256, 256, t);
	}
	
	protected void renderItemIn2D(Tessellator tess, float texU0, float texV0, float texU1, float texV1, int iconwidth, int iconheight, float thickness)
	{
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, 1.0F);
		tess.addVertexWithUV(0.0D, 0.0D, 0.0D, texU0, texV1);
		tess.addVertexWithUV(1.0D, 0.0D, 0.0D, texU1, texV1);
		tess.addVertexWithUV(1.0D, 1.0D, 0.0D, texU1, texV0);
		tess.addVertexWithUV(0.0D, 1.0D, 0.0D, texU0, texV0);
		//tess.draw();
		//tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, -1.0F);
		tess.addVertexWithUV(0.0D, 1.0D, (0.0F - thickness), texU0, texV0);
		tess.addVertexWithUV(1.0D, 1.0D, (0.0F - thickness), texU1, texV0);
		tess.addVertexWithUV(1.0D, 0.0D, (0.0F - thickness), texU1, texV1);
		tess.addVertexWithUV(0.0D, 0.0D, (0.0F - thickness), texU0, texV1);
		//tess.draw();
		float f5 = iconwidth * (texU0 - texU1);
		float f6 = iconheight * (texV1 - texV0);
		//tess.startDrawingQuads();
		tess.setNormal(-1.0F, 0.0F, 0.0F);
		int k;
		float f7;
		float f8;
		
		for (k = 0; k < f5; ++k)
		{
			f7 = k / f5;
			f8 = texU0 + (texU1 - texU0) * f7 - 0.5F / iconwidth;
			tess.addVertexWithUV(f7, 0.0D, (0.0F - thickness), f8, texV1);
			tess.addVertexWithUV(f7, 0.0D, 0.0D, f8, texV1);
			tess.addVertexWithUV(f7, 1.0D, 0.0D, f8, texV0);
			tess.addVertexWithUV(f7, 1.0D, (0.0F - thickness), f8, texV0);
		}
		
		//tess.draw();
		//tess.startDrawingQuads();
		tess.setNormal(1.0F, 0.0F, 0.0F);
		float f9;
		
		for (k = 0; k < f5; ++k)
		{
			f7 = k / f5;
			f8 = texU0 + (texU1 - texU0) * f7 - 0.5F / iconwidth;
			f9 = f7 + 1.0F / f5;
			tess.addVertexWithUV(f9, 1.0D, (0.0F - thickness), f8, texV0);
			tess.addVertexWithUV(f9, 1.0D, 0.0D, f8, texV0);
			tess.addVertexWithUV(f9, 0.0D, 0.0D, f8, texV1);
			tess.addVertexWithUV(f9, 0.0D, (0.0F - thickness), f8, texV1);
		}
		
		//tess.draw();
		//tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		
		for (k = 0; k < f6; ++k)
		{
			f7 = k / f6;
			f8 = texV1 + (texV0 - texV1) * f7 - 0.5F / iconheight;
			f9 = f7 + 1.0F / f6;
			tess.addVertexWithUV(0.0D, f9, 0.0D, texU0, f8);
			tess.addVertexWithUV(1.0D, f9, 0.0D, texU1, f8);
			tess.addVertexWithUV(1.0D, f9, (0.0F - thickness), texU1, f8);
			tess.addVertexWithUV(0.0D, f9, (0.0F - thickness), texU0, f8);
		}
		
		//tess.draw();
		//tess.startDrawingQuads();
		tess.setNormal(0.0F, -1.0F, 0.0F);
		
		for (k = 0; k < f6; ++k)
		{
			f7 = k / f6;
			f8 = texV1 + (texV0 - texV1) * f7 - 0.5F / iconheight;
			tess.addVertexWithUV(1.0D, f7, 0.0D, texU1, f8);
			tess.addVertexWithUV(0.0D, f7, 0.0D, texU0, f8);
			tess.addVertexWithUV(0.0D, f7, (0.0F - thickness), texU0, f8);
			tess.addVertexWithUV(1.0D, f7, (0.0F - thickness), texU1, f8);
		}
		
		tess.draw();
	}
	
	protected void renderEnchantEffect(Tessellator tess, ItemStack item, int iconwidth, int iconheight, float thickness)
	{
		if (item != null && item.hasEffect(0))
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			mc.renderEngine.bindTexture(WeaponModResources.Textures.ENCHANTMENT_GLINT);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			float var14 = 0.76F;
			GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float var15 = 0.125F;
			GL11.glScalef(var15, var15, var15);
			float var16 = (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
			GL11.glTranslatef(var16, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			renderItemIn2D(tess, 0.0F, 0.0F, 1.0F, 1.0F, iconwidth, iconheight, thickness);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(var15, var15, var15);
			var16 = (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
			GL11.glTranslatef(-var16, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			renderItemIn2D(tess, 0.0F, 0.0F, 1.0F, 1.0F, iconwidth, iconheight, thickness);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}
}
