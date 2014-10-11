package ckathode.weaponmod.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonStandard extends ModelBase
{
	//fields
	public ModelRenderer	console_main_;
	public ModelRenderer	console_side_l1;
	public ModelRenderer	console_side_r1;
	public ModelRenderer	base_1;
	public ModelRenderer	base_2;
	public ModelRenderer	base_stand;
	public ModelRenderer	axis1;
	
	public ModelCannonStandard()
	{
		textureWidth = 32;
		textureHeight = 32;
		
		console_main_ = new ModelRenderer(this, 12, 20);
		console_main_.addBox(-2.5F, -1F, -1F, 5, 1, 2);
		console_main_.setRotationPoint(0F, 20F, 0F);
		console_main_.setTextureSize(32, 32);
		console_main_.mirror = true;
		setRotation(console_main_, 0F, 0F, 0F);
		console_side_l1 = new ModelRenderer(this, 26, 20);
		console_side_l1.addBox(2.5F, -4F, -1F, 1, 5, 2);
		console_side_l1.setRotationPoint(0F, 19F, 0F);
		console_side_l1.setTextureSize(32, 32);
		console_side_l1.mirror = true;
		setRotation(console_side_l1, 0F, 0F, 0F);
		console_side_r1 = new ModelRenderer(this, 26, 20);
		console_side_r1.addBox(-3.5F, -4F, -1F, 1, 5, 2);
		console_side_r1.setRotationPoint(0F, 19F, 0F);
		console_side_r1.setTextureSize(32, 32);
		console_side_r1.mirror = true;
		setRotation(console_side_r1, 0F, 0F, 0F);
		base_1 = new ModelRenderer(this, 0, 26);
		base_1.addBox(-2F, -2F, -2F, 4, 2, 4);
		base_1.setRotationPoint(0F, 24F, 0F);
		base_1.setTextureSize(32, 32);
		base_1.mirror = true;
		setRotation(base_1, 0F, 0F, 0F);
		base_2 = new ModelRenderer(this, 16, 28);
		base_2.addBox(-1.5F, -3F, -1.5F, 3, 1, 3);
		base_2.setRotationPoint(0F, 24F, 0F);
		base_2.setTextureSize(32, 32);
		base_2.mirror = true;
		setRotation(base_2, 0F, 0F, 0F);
		base_stand = new ModelRenderer(this, 0, 23);
		base_stand.addBox(-1F, -4F, -1F, 2, 1, 2);
		base_stand.setRotationPoint(0F, 24F, 0F);
		base_stand.setTextureSize(32, 32);
		base_stand.mirror = true;
		setRotation(base_stand, 0F, 0F, 0F);
		axis1 = new ModelRenderer(this, 22, 23);
		axis1.addBox(-0.5F, -5.5F, -0.5F, 1, 3, 1);
		axis1.setRotationPoint(0F, 24F, 0F);
		axis1.setTextureSize(32, 32);
		axis1.mirror = true;
		setRotation(axis1, 0F, 0F, 0F);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		console_main_.render(f5);
		console_side_l1.render(f5);
		console_side_r1.render(f5);
		base_1.render(f5);
		base_2.render(f5);
		base_stand.render(f5);
		axis1.render(f5);
	}
	
	public void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
	
}
