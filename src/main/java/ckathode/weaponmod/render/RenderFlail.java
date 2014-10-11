package ckathode.weaponmod.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityFlail;

public class RenderFlail extends Render
{
	public void renderFlail(EntityFlail entityarrow, double d, double d1, double d2, float f, float f1)
	{
		bindEntityTexture(entityarrow);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef((entityarrow.prevRotationYaw + (entityarrow.rotationYaw - entityarrow.prevRotationYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entityarrow.prevRotationPitch + (entityarrow.rotationPitch - entityarrow.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		
		// ===============FLAIL BALL===============
		float[] color = entityarrow.getMaterialColor();
		int i = 0;
		float f2 = 0.0F;
		float f3 = 0.5F;
		float f4 = (0 + i * 10) / 32F;
		float f5 = (5 + i * 10) / 32F;
		float f6 = 0.0F;
		float f7 = 0.15625F;
		float f8 = (5 + i * 10) / 32F;
		float f9 = (10 + i * 10) / 32F;
		float f10 = 0.15F;
		GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
		float f11 = -f1;
		if (f11 > 0.0F)
		{
			float f12 = -MathHelper.sin(f11 * 3F) * f11;
			GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
		}
		
		GL11.glColor3f(color[0], color[1], color[2]);
		GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(f10, f10, f10);
		GL11.glTranslatef(-4F, 0.0F, 0.0F);
		GL11.glNormal3f(f10, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(1.5D, -2D, -2D, f6, f8);
		tessellator.addVertexWithUV(1.5D, -2D, 2D, f7, f8);
		tessellator.addVertexWithUV(1.5D, 2D, 2D, f7, f9);
		tessellator.addVertexWithUV(1.5D, 2D, -2D, f6, f9);
		tessellator.draw();
		GL11.glNormal3f(-f10, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(1.5D, 2D, -2D, f6, f8);
		tessellator.addVertexWithUV(1.5D, 2D, 2D, f7, f8);
		tessellator.addVertexWithUV(1.5D, -2D, 2D, f7, f9);
		tessellator.addVertexWithUV(1.5D, -2D, -2D, f6, f9);
		tessellator.draw();
		for (int j = 0; j < 4; j++)
		{
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f10);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-8D, -2D, 0.0D, f2, f4);
			tessellator.addVertexWithUV(8D, -2D, 0.0D, f3, f4);
			tessellator.addVertexWithUV(8D, 2D, 0.0D, f3, f5);
			tessellator.addVertexWithUV(-8D, 2D, 0.0D, f2, f5);
			tessellator.draw();
		}
		
		GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		GL11.glPopMatrix();
		// =====================END========================
		
		// =====================FISH LINE========================
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		byte byte0 = 2;
		float f13 = (i * 8 + 0) / 128F; // 2
		float f14 = (i * 8 + 8) / 128F; // 3
		float f15 = (byte0 * 8 + 0) / 128F; // 4
		float f16 = (byte0 * 8 + 8) / 128F; // 5
		float f17 = 1.0F; // 6
		float f18 = 0.5F; // 7
		float f19 = 0.5F; // 8
		GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f18, 0.0F - f19, 0.0D, f13, f16);
		tessellator.addVertexWithUV(f17 - f18, 0.0F - f19, 0.0D, f14, f16);
		tessellator.addVertexWithUV(f17 - f18, 1.0F - f19, 0.0D, f14, f15);
		tessellator.addVertexWithUV(0.0F - f18, 1.0F - f19, 0.0D, f13, f15);
		tessellator.draw();
		GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		GL11.glPopMatrix();
		if (entityarrow.shootingEntity instanceof EntityLivingBase)
		{
			float f22 = ((EntityLivingBase) entityarrow.shootingEntity).getSwingProgress(f1); // 11
			float f23 = MathHelper.sin(MathHelper.sqrt_float(f22) * 3.141593F); // 12
			Vec3 vec3d = Vec3.createVectorHelper(-0.5D, 0.03D, 0.8D);
			vec3d.rotateAroundX((-(entityarrow.shootingEntity.prevRotationPitch + (entityarrow.shootingEntity.rotationPitch - entityarrow.shootingEntity.prevRotationPitch) * f1) * 3.141593F) / 180F);
			vec3d.rotateAroundY((-(entityarrow.shootingEntity.prevRotationYaw + (entityarrow.shootingEntity.rotationYaw - entityarrow.shootingEntity.prevRotationYaw) * f1) * 3.141593F) / 180F);
			vec3d.rotateAroundY(f23 * 0.5F);
			vec3d.rotateAroundX(-f23 * 0.7F);
			double d7 = entityarrow.shootingEntity.prevPosX + (entityarrow.shootingEntity.posX - entityarrow.shootingEntity.prevPosX) * f1 + vec3d.xCoord;
			double d8 = entityarrow.shootingEntity.prevPosY + (entityarrow.shootingEntity.posY - entityarrow.shootingEntity.prevPosY) * f1 + vec3d.yCoord;
			double d9 = entityarrow.shootingEntity.prevPosZ + (entityarrow.shootingEntity.posZ - entityarrow.shootingEntity.prevPosZ) * f1 + vec3d.zCoord;
			if (renderManager.options.thirdPersonView > 0)
			{
				float f21 = ((((EntityLivingBase) entityarrow.shootingEntity).prevRenderYawOffset + (((EntityLivingBase) entityarrow.shootingEntity).renderYawOffset - ((EntityLivingBase) entityarrow.shootingEntity).prevRenderYawOffset) * f1) * 3.141593F) / 180F;
				double d4 = MathHelper.sin(f21);
				double d6 = MathHelper.cos(f21);
				d7 = (entityarrow.shootingEntity.prevPosX + (entityarrow.shootingEntity.posX - entityarrow.shootingEntity.prevPosX) * f1) - d6 * 0.35D - d4 * 0.85D;
				d8 = (entityarrow.shootingEntity.prevPosY + (entityarrow.shootingEntity.posY - entityarrow.shootingEntity.prevPosY) * f1) - 0.45D;
				d9 = ((entityarrow.shootingEntity.prevPosZ + (entityarrow.shootingEntity.posZ - entityarrow.shootingEntity.prevPosZ) * f1) - d4 * 0.35D) + d6 * 0.85D;
			}
			double d10 = entityarrow.prevPosX + (entityarrow.posX - entityarrow.prevPosX) * f1;
			double d11 = entityarrow.prevPosY + (entityarrow.posY - entityarrow.prevPosY) * f1 + 0.25D;
			double d12 = entityarrow.prevPosZ + (entityarrow.posZ - entityarrow.prevPosZ) * f1;
			double d13 = (float) (d7 - d10);
			double d14 = (float) (d8 - d11);
			double d15 = (float) (d9 - d12);
			GL11.glDisable(3553 /* GL_TEXTURE_2D */);
			GL11.glDisable(2896 /* GL_LIGHTING */);
			tessellator.startDrawing(GL11.GL_LINE_STRIP);
			tessellator.setColorOpaque_I(0);
			int j = 16;
			for (int k = 0; k <= j; k++)
			{
				float f24 = (float) k / (float) j;
				tessellator.addVertex(d + d13 * f24, d1 + d14 * (f24 * f24 + f24) * 0.5D + 0.25D, d2 + d15 * f24);
			}
			
			tessellator.draw();
			GL11.glEnable(2896 /* GL_LIGHTING */);
			GL11.glEnable(3553 /* GL_TEXTURE_2D */);
		}
		// =====================END========================
	}
	
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
	{
		renderFlail((EntityFlail) entity, d, d1, d2, f, f1);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return WeaponModResources.Textures.FLAIL;
	}
}
