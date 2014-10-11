package ckathode.weaponmod.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityJavelin;

public class RenderJavelin extends Render
{
	public void renderJavelin(EntityJavelin entityarrow, double d, double d1, double d2, float f, float f1)
	{
		bindEntityTexture(entityarrow);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef((entityarrow.prevRotationYaw + (entityarrow.rotationYaw - entityarrow.prevRotationYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entityarrow.prevRotationPitch + (entityarrow.rotationPitch - entityarrow.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
		Tessellator tess = Tessellator.instance;
		int i = 0;
		float f2 = 0.0F;
		float f3 = 1.0F;
		float f4 = (0 + i * 10) / 32F;
		float f5 = (5 + i * 10) / 32F;
		float f6 = 0.0F;
		float f7 = 0.15625F;
		float f8 = (5 + i * 10) / 32F;
		float f9 = (10 + i * 10) / 32F;
		float f10 = 0.05625F;
		
		double length = 20D;
		
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		float f11 = entityarrow.arrowShake - f1;
		if (f11 > 0.0F)
		{
			float f12 = -MathHelper.sin(f11 * 3F) * f11;
			GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
		}
		GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(f10, f10, f10);
		GL11.glTranslatef(-4F, 0.0F, 0.0F);
		GL11.glNormal3f(f10, 0.0F, 0.0F);
		tess.startDrawingQuads();
		tess.addVertexWithUV(-length, -2D, -2D, f6, f8);
		tess.addVertexWithUV(-length, -2D, 2D, f7, f8);
		tess.addVertexWithUV(-length, 2D, 2D, f7, f9);
		tess.addVertexWithUV(-length, 2D, -2D, f6, f9);
		tess.draw();
		
		GL11.glNormal3f(-f10, 0F, 0F);
		tess.startDrawingQuads();
		tess.addVertexWithUV(-length, 2D, -2D, f6, f8);
		tess.addVertexWithUV(-length, 2D, 2D, f7, f8);
		tess.addVertexWithUV(-length, -2D, 2D, f7, f9);
		tess.addVertexWithUV(-length, -2D, -2D, f6, f9);
		tess.draw();
		
		for (int j = 0; j < 4; j++)
		{
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f10);
			tess.startDrawingQuads();
			tess.setColorOpaque_F(1F, 1F, 1F);
			tess.addVertexWithUV(-length, -2D, 0.0D, f2, f4);
			tess.addVertexWithUV(length, -2D, 0.0D, f3, f4);
			tess.addVertexWithUV(length, 2D, 0.0D, f3, f5);
			tess.addVertexWithUV(-length, 2D, 0.0D, f2, f5);
			tess.draw();
		}
		
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
	{
		renderJavelin((EntityJavelin) entity, d, d1, d2, f, f1);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return WeaponModResources.Textures.JAVELIN;
	}
}
