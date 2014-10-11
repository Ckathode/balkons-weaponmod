package ckathode.weaponmod.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;

public class RenderBoomerang extends Render
{
	private void renderBoomerang(EntityBoomerang entityarrow, double d, double d1, double d2, float f, float f1)
	{
		bindEntityTexture(entityarrow);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(entityarrow.prevRotationPitch + (entityarrow.rotationPitch - entityarrow.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef((entityarrow.prevRotationYaw + (entityarrow.rotationYaw - entityarrow.prevRotationYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
		Tessellator tess = Tessellator.instance;
		
		int mat = entityarrow.getWeaponMaterialId();
		float[] color = entityarrow.getMaterialColor();
		float ft0 = 0.0F;
		float ft1 = 0.5F;
		float ft2 = 1.0F;
		
		float fh = 0.08F;
		float f2 = 0.2F;
		float f3 = 0.9F;
		float f4 = 1F - f2;
		
		float ft3 = 0.5F;
		float ft4 = 0.65625F;
		
		GL11.glTranslatef(-0.5F, 0F, -0.5F);
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glNormal3f(0F, 1F, 0F);
		tess.startDrawingQuads();
		tess.setColorOpaque_F(1F, 1F, 1F);
		tess.addVertexWithUV(0D, 0D, 1D, ft1, ft0);
		tess.addVertexWithUV(1D, 0D, 1D, ft0, ft0);
		tess.addVertexWithUV(1D, 0D, 0D, ft0, ft1);
		tess.addVertexWithUV(0D, 0D, 0D, ft1, ft1);
		
		if (mat != 0)
		{
			tess.setColorOpaque_F(color[0], color[1], color[2]);
			tess.addVertexWithUV(0D, 0D, 1D, ft2, ft0);
			tess.addVertexWithUV(1D, 0D, 1D, ft1, ft0);
			tess.addVertexWithUV(1D, 0D, 0D, ft1, ft1);
			tess.addVertexWithUV(0D, 0D, 0D, ft2, ft1);
		}
		tess.draw();
		GL11.glNormal3f(0F, -1F, 0F);
		tess.startDrawingQuads();
		tess.setColorOpaque_F(1F, 1F, 1F);
		tess.addVertexWithUV(1D, 0D, 0D, ft0, ft1);
		tess.addVertexWithUV(1D, 0D, 1D, ft1, ft1);
		tess.addVertexWithUV(0D, 0D, 1D, ft1, ft0);
		tess.addVertexWithUV(0D, 0D, 0D, ft0, ft0);
		
		if (mat != 0)
		{
			tess.setColorOpaque_F(color[0], color[1], color[2]);
			tess.addVertexWithUV(1D, 0D, 0D, ft1, ft1);
			tess.addVertexWithUV(1D, 0D, 1D, ft2, ft1);
			tess.addVertexWithUV(0D, 0D, 1D, ft2, ft0);
			tess.addVertexWithUV(0D, 0D, 0D, ft1, ft0);
		}
		tess.draw();
		
		float sqrt2 = (float) Math.sqrt(2);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glNormal3f(-sqrt2, 0F, sqrt2);
		tess.startDrawingQuads();
		tess.setColorOpaque_F(1F, 1F, 1F);
		tess.addVertexWithUV(f2, -fh, f4, ft1, ft3);
		tess.addVertexWithUV(f2, fh, f4, ft1, ft4);
		tess.addVertexWithUV(f3, fh, f4, ft0, ft4);
		tess.addVertexWithUV(f3, -fh, f4, ft0, ft3);
		
		if (mat != 0)
		{
			tess.setColorOpaque_F(color[0], color[1], color[2]);
			tess.addVertexWithUV(f2, -fh, f4, ft2, ft3);
			tess.addVertexWithUV(f2, fh, f4, ft2, ft4);
			tess.addVertexWithUV(f3, fh, f4, ft1, ft4);
			tess.addVertexWithUV(f3, -fh, f4, ft1, ft3);
		}
		
		tess.setColorOpaque_F(1F, 1F, 1F);
		tess.addVertexWithUV(f2, -fh, f4, ft1, ft3);
		tess.addVertexWithUV(f2, fh, f4, ft1, ft4);
		tess.addVertexWithUV(f2, fh, f2, ft0, ft4);
		tess.addVertexWithUV(f2, -fh, f2, ft0, ft3);
		
		if (mat != 0)
		{
			tess.setColorOpaque_F(color[0], color[1], color[2]);
			tess.addVertexWithUV(f2, -fh, f4, ft2, ft3);
			tess.addVertexWithUV(f2, fh, f4, ft2, ft4);
			tess.addVertexWithUV(f2, fh, f2, ft1, ft4);
			tess.addVertexWithUV(f2, -fh, f2, ft1, ft3);
		}
		tess.draw();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
	{
		renderBoomerang((EntityBoomerang) var1, var2, var4, var6, var8, var9);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return WeaponModResources.Textures.BOOMERANG;
	}
}
