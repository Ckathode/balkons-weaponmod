package ckathode.weaponmod.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityDummy;

public class RenderDummy extends Render
{
	private ModelDummy	modelDummy;
	
	public RenderDummy()
	{
		shadowSize = 1.0F;
		modelDummy = new ModelDummy();
	}
	
	public void renderDummy(EntityDummy entitydummy, double d, double d1, double d2, float f, float f1)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1 - 0.4F, (float) d2);
		GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
		float f3 = entitydummy.getTimeSinceHit() - f1;
		float f4 = entitydummy.getCurrentDamage() - f1;
		if (f4 < 0.0F)
		{
			f4 = 0.0F;
		}
		if (f3 > 0.0F)
		{
			GL11.glRotatef((((MathHelper.sin(f3) * f3 * f4) / 10F) * entitydummy.getRockDirection()) / 5, 0.0F, 0.0F, 1.0F);
		}
		bindEntityTexture(entitydummy);
		GL11.glScalef(-1F, -1F, 1.0F);
		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		modelDummy.render(entitydummy, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
	{
		renderDummy((EntityDummy) entity, d, d1, d2, f, f1);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return WeaponModResources.Textures.DUMMY;
	}
}
