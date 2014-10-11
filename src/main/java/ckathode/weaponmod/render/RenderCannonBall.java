package ckathode.weaponmod.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;

public class RenderCannonBall extends Render
{
	public RenderCannonBall()
	{
		shadowSize = 0.5F;
	}
	
	public void renderCannonBall(EntityCannonBall entitycannonball, double d, double d1, double d2, float f, float f1)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glPushMatrix();
		bindEntityTexture(entitycannonball);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1F, -1F, 1.0F);
		GL11.glScalef(0.7F, 0.7F, 0.7F);
		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-0.5F, +0.5F, -0.5F, 0F, 1F);
		tessellator.addVertexWithUV(+0.5F, +0.5F, -0.5F, 1F, 1F);
		tessellator.addVertexWithUV(+0.5F, -0.5F, -0.5F, 1F, 0F);
		tessellator.addVertexWithUV(-0.5F, -0.5F, -0.5F, 0F, 0F);
		
		tessellator.addVertexWithUV(-0.5F, -0.5F, +0.5F, 0F, 0F);
		tessellator.addVertexWithUV(+0.5F, -0.5F, +0.5F, 1F, 0F);
		tessellator.addVertexWithUV(+0.5F, +0.5F, +0.5F, 1F, 1F);
		tessellator.addVertexWithUV(-0.5F, +0.5F, +0.5F, 0F, 1F);
		
		tessellator.addVertexWithUV(-0.5F, -0.5F, -0.5F, 0F, 0F);
		tessellator.addVertexWithUV(+0.5F, -0.5F, -0.5F, 1F, 0F);
		tessellator.addVertexWithUV(+0.5F, -0.5F, +0.5F, 1F, 1F);
		tessellator.addVertexWithUV(-0.5F, -0.5F, +0.5F, 0F, 1F);
		
		tessellator.addVertexWithUV(-0.5F, +0.5F, +0.5F, 0F, 1F);
		tessellator.addVertexWithUV(+0.5F, +0.5F, +0.5F, 1F, 1F);
		tessellator.addVertexWithUV(+0.5F, +0.5F, -0.5F, 1F, 0F);
		tessellator.addVertexWithUV(-0.5F, +0.5F, -0.5F, 0F, 0F);
		
		tessellator.addVertexWithUV(-0.5F, -0.5F, +0.5F, 0F, 0F);
		tessellator.addVertexWithUV(-0.5F, +0.5F, +0.5F, 1F, 0F);
		tessellator.addVertexWithUV(-0.5F, +0.5F, -0.5F, 1F, 1F);
		tessellator.addVertexWithUV(-0.5F, -0.5F, -0.5F, 0F, 1F);
		
		tessellator.addVertexWithUV(+0.5F, -0.5F, -0.5F, 0F, 0F);
		tessellator.addVertexWithUV(+0.5F, +0.5F, -0.5F, 1F, 0F);
		tessellator.addVertexWithUV(+0.5F, +0.5F, +0.5F, 1F, 1F);
		tessellator.addVertexWithUV(+0.5F, -0.5F, +0.5F, 0F, 1F);
		tessellator.draw();
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
	{
		renderCannonBall((EntityCannonBall) entity, d, d1, d2, f, f1);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return WeaponModResources.Textures.CANNONBALL;
	}
}
