package ckathode.weaponmod;

import ckathode.weaponmod.item.ItemMelee;
import ckathode.weaponmod.item.MeleeCompQuarterStaff;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.MeleeComponent.MeleeSpecs;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Weaponblockdamage
{

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
    	if (event.entityLiving instanceof EntityPlayer)
    	{
	        EntityPlayer player = (EntityPlayer) event.entityLiving;
	        ItemStack weapon = player.getItemInUse();
	        if (weapon != null && weapon.getItem() instanceof ItemMelee && !event.source.isUnblockable() && !event.source.isFireDamage() && !event.source.isDamageAbsolute())
	        {
		       	//((ItemMelee) BalkonsWeaponMod.battleaxeDiamond).getMeleeComponent().meleeSpecs.BATTLEAXE;
		        MeleeComponent meleeComponent = ((ItemMelee)weapon.getItem()).getMeleeComponent();
		        if (((ItemMelee)weapon.getItem()).getMeleeComponent().meleeSpecs == MeleeSpecs.QUARTERSTAFF)
	           {
		        	double x = player.posX;
		        	double y = player.posY;
		        	double z = player.posZ;
		        	
	            	float ignoreAmount = ((MeleeCompQuarterStaff) meleeComponent).blockmount + ((MeleeCompQuarterStaff) meleeComponent).blockextra;
	            	weapon.damageItem(1, event.entityLiving);
	                event.ammount -= ignoreAmount;
	                if (event.ammount < 0){
	                	event.ammount = 0;
			        	player.worldObj.playSoundEffect(x, y, z, "random.break", 3F, 1F / (((ItemMelee) weapon.getItem()).getItemRand().nextFloat() * 0.4F + 0.7F));
	                }
	                else{

			        	player.worldObj.playSoundEffect(x, y, z, "random.break", 3F, 1F / (((ItemMelee) weapon.getItem()).getItemRand().nextFloat() * 0.4F + 0.2F));
	                }
	            }
	            else
	            {
	            	
	            }
	        }
    	}
        
    }
    
    
    
}
