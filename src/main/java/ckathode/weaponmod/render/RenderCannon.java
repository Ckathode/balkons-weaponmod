package ckathode.weaponmod.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;

public class RenderCannon extends Render
{
	protected ModelCannon			modelCannon;
	protected ModelCannonBarrel		modelBarrel;
	protected ModelCannonStandard	modelStandard;
	
	public RenderCannon()
	{
		shadowSize = 1.0F;
		modelCannon = new ModelCannon();
		modelBarrel = new ModelCannonBarrel();
		modelStandard = new ModelCannonStandard();
	}
	
	private void renderCannon(EntityCannon entitycannon, double d, double d1, double d2, float f, float f1)
	{
		GL11.glPushMatrix();
		
		float rot = entitycannon.prevRotationPitch + (entitycannon.rotationPitch - entitycannon.prevRotationPitch) * f1;
		rot = Math.min(rot, 20F);
		
		GL11.glTranslatef((float) d, (float) d1 + 1.9F, (float) d2);
		GL11.glRotatef(-f, 0.0F, 1.0F, 0.0F);
		float f3 = entitycannon.getTimeSinceHit() - f1;
		float f4 = entitycannon.getCurrentDamage() - f1;
		if (f4 < 0.0F)
		{
			f4 = 0.0F;
		}
		if (f3 > 0.0F)
		{
			GL11.glRotatef((((MathHelper.sin(f3) * f3 * f4) / 10F) * entitycannon.getRockDirection()) / 5, 0.0F, 0.0F, 1.0F);
		}
		bindEntityTexture(entitycannon);
		GL11.glScalef(-1.6F, -1.6F, 1.6F);
		if (entitycannon.isSuperPowered() && entitycannon.ticksExisted % 5 < 2)
		{
			float f5 = 1.5F;
			GL11.glColor3f(entitycannon.getBrightness(0F) * f5, entitycannon.getBrightness(0F) * f5, entitycannon.getBrightness(0F) * f5);
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0F, 1F, 0F);
		GL11.glRotatef(rot, 1F, 0F, 0F);
		GL11.glTranslatef(0F, -1F, 0F);
		modelBarrel.render(entitycannon, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		
		float yaw = -(float) Math.toRadians(f);
		modelStandard.base_1.rotateAngleY = yaw;
		modelStandard.base_2.rotateAngleY = yaw;
		modelStandard.base_stand.rotateAngleY = yaw;
		modelStandard.render(entitycannon, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
	{
		renderCannon((EntityCannon) entity, d, d1, d2, f, f1);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return WeaponModResources.Textures.CANNON;
	}
}
