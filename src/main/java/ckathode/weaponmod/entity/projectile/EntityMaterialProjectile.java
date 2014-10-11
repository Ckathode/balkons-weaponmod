package ckathode.weaponmod.entity.projectile;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ckathode.weaponmod.item.IItemWeapon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class EntityMaterialProjectile extends EntityProjectile
{
	private static final float[][]	MATERIAL_COLORS	= { { 0.6F, 0.4F, 0.1F }, { 0.5F, 0.5F, 0.5F }, { 1.0F, 1.0F, 1.0F }, { 0.0F, 0.8F, 0.7F }, { 1.0F, 0.9F, 0.0F } };
	
	protected ItemStack				thrownItem;
	
	public EntityMaterialProjectile(World world)
	{
		super(world);
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(25, Byte.valueOf((byte) 0));
	}
	
	public float getMeleeHitDamage(Entity entity)
	{
		if (shootingEntity instanceof EntityLivingBase && entity instanceof EntityLivingBase)
		{
			return EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase) shootingEntity, (EntityLivingBase) entity);
		}
		return 0F;
	}
	
	@Override
	public void applyEntityHitEffects(Entity entity)
	{
		super.applyEntityHitEffects(entity);
		if (shootingEntity instanceof EntityLivingBase && entity instanceof EntityLivingBase)
		{
			int i = EnchantmentHelper.getKnockbackModifier((EntityLivingBase) shootingEntity, (EntityLivingBase) entity);
			if (i != 0)
			{
				entity.addVelocity(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F, 0.1D, MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F);
			}
			
			i = EnchantmentHelper.getFireAspectModifier((EntityLivingBase) shootingEntity);
			if (i > 0 && !entity.isBurning())
			{
				entity.setFire(1);
			}
		}
	}
	
	public void setThrownItemStack(ItemStack itemstack)
	{
		thrownItem = itemstack;
		updateWeaponMaterial();
	}
	
	@Override
	public ItemStack getPickupItem()
	{
		return thrownItem;
	}
	
	public int getWeaponMaterialId()
	{
		return dataWatcher.getWatchableObjectByte(25);
	}
	
	protected final void updateWeaponMaterial()
	{
		if (thrownItem != null && thrownItem.getItem() instanceof IItemWeapon && ((IItemWeapon) thrownItem.getItem()).getMeleeComponent() != null)
		{
			int material = MaterialRegistry.getMaterialID(thrownItem);
			if (material < 0)
			{
				material = ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().weaponMaterial.ordinal();
			}
			dataWatcher.updateObject(25, Byte.valueOf((byte) (material & 0xFF)));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public final float[] getMaterialColor()
	{
		int id = getWeaponMaterialId();
		if (id < 5)
		{
			return MATERIAL_COLORS[id];
		}
		return MaterialRegistry.getColorFromMaterialID(id);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		if (thrownItem != null)
		{
			nbttagcompound.setTag("thrI", thrownItem.writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		setThrownItemStack(ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("thrI")));
	}
}
