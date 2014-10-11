package ckathode.weaponmod;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import ckathode.weaponmod.network.MsgExplosion;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public abstract class PhysHelper
{
	private static double	kbMotionX			= 0D;
	private static double	kbMotionY			= 0D;
	private static double	kbMotionZ			= 0D;
	private static int		knockBackModifier	= 0;
	
	public static AdvancedExplosion createStandardExplosion(World world, Entity entity, double d, double d1, double d2, float size)
	{
		AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size);
		explosion.doEntityExplosion();
		explosion.doBlockExplosion();
		explosion.doParticleExplosion(true, true);
		sendExplosion(world, explosion, true, true);
		return explosion;
	}
	
	public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity, double d, double d1, double d2, float size, boolean destroyBlocks, boolean spawnSmallParticles, boolean spawnBigParticles)
	{
		AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size);
		explosion.doEntityExplosion();
		if (destroyBlocks)
		{
			explosion.doBlockExplosion();
		}
		explosion.doParticleExplosion(spawnSmallParticles, spawnBigParticles);
		sendExplosion(world, explosion, spawnSmallParticles, spawnBigParticles);
		return explosion;
	}
	
	public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity, DamageSource damagesource, double d, double d1, double d2, float size, boolean destroyBlocks, boolean spawnSmallParticles, boolean spawnBigParticles)
	{
		AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size);
		explosion.doEntityExplosion(damagesource);
		if (destroyBlocks)
		{
			explosion.doBlockExplosion();
		}
		explosion.doParticleExplosion(spawnSmallParticles, spawnBigParticles);
		sendExplosion(world, explosion, spawnSmallParticles, spawnBigParticles);
		return explosion;
	}
	
	public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity, double d, double d1, double d2, float size, boolean destroyBlocks, boolean spawnParticles)
	{
		AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size);
		explosion.doEntityExplosion();
		if (destroyBlocks)
		{
			explosion.doBlockExplosion();
		}
		explosion.doParticleExplosion(spawnParticles, spawnParticles);
		sendExplosion(world, explosion, spawnParticles, spawnParticles);
		return explosion;
	}
	
	private static void sendExplosion(World world, AdvancedExplosion explosion, boolean smallparts, boolean bigparts)
	{
		if (world instanceof WorldServer && !world.isRemote)
		{
			MsgExplosion msg = new MsgExplosion(explosion, smallparts, bigparts);
			BalkonsWeaponMod.instance.messagePipeline.sendToAllAround(msg, new TargetPoint(world.provider.dimensionId, explosion.explosionX, explosion.explosionY, explosion.explosionZ, 64D));
		}
	}
	
	public static void knockBack(EntityLivingBase entityliving, EntityLivingBase attacker, float knockback)
	{
		entityliving.motionX = kbMotionX;
		entityliving.motionY = kbMotionY;
		entityliving.motionZ = kbMotionZ;
		//float f2 = 1F / 0.4F;
		
		//attackEntityFrom part
		double dx = attacker.posX - entityliving.posX;
		double dz;
		
		for (dz = attacker.posZ - entityliving.posZ; dx * dx + dz * dz < 1E-4D; dz = (Math.random() - Math.random()) * 0.01D)
		{
			dx = (Math.random() - Math.random()) * 0.01D;
		}
		
		entityliving.attackedAtYaw = (float) ((Math.atan2(dz, dx) * 180D) / Math.PI) - entityliving.rotationYaw;
		
		//knockBack part
		float f = MathHelper.sqrt_double(dx * dx + dz * dz);
		entityliving.motionX -= (dx / f) * knockback;
		entityliving.motionY += knockback;
		entityliving.motionZ -= (dz / f) * knockback;
		if (entityliving.motionY > 0.4D)
		{
			entityliving.motionY = 0.4D;
		}
		
		if (knockBackModifier > 0)
		{
			dx = -Math.sin(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5F;
			dz = Math.cos(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5F;
			entityliving.addVelocity(dx, 0.1D, dz);
		}
		
		knockBackModifier = 0;
		kbMotionX = kbMotionY = kbMotionZ = 0D;
	}
	
	public static void prepareKnockbackOnEntity(EntityLivingBase attacker, EntityLivingBase entity)
	{
		knockBackModifier = EnchantmentHelper.getKnockbackModifier(attacker, entity);
		if (attacker.isSprinting())
		{
			knockBackModifier++;
		}
		kbMotionX = entity.motionX;
		kbMotionY = entity.motionY;
		kbMotionZ = entity.motionZ;
	}
}
