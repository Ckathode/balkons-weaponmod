package ckathode.weaponmod;

import net.minecraftforge.common.MinecraftForge;
import ckathode.weaponmod.network.MsgCannonFire;
import ckathode.weaponmod.network.MsgExplosion;
import ckathode.weaponmod.network.WMMessagePipeline;
import net.minecraftforge.fml.relauncher.Side;

public class WMCommonProxy
{
	public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new WMCommonEventHandler());
	}
	
	public void registerPackets(WMMessagePipeline pipeline)
	{
		pipeline.registerMessage(MsgCannonFire.Handler.class, MsgCannonFire.class, 0, Side.SERVER);
		pipeline.registerMessage(MsgExplosion.Handler.class, MsgExplosion.class, 1, Side.CLIENT);
	}
	
	public void registerIcons()
	{
	}
	
	public void registerRenderers(WeaponModConfig config)
	{
	}
}
