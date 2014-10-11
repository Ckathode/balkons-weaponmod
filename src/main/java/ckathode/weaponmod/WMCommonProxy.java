package ckathode.weaponmod;

import net.minecraftforge.common.MinecraftForge;
import ckathode.weaponmod.network.MsgCannonFire;
import ckathode.weaponmod.network.MsgExplosion;
import ckathode.weaponmod.network.WMMessagePipeline;

public class WMCommonProxy
{
	public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new WMCommonEventHandler());
	}
	
	public void registerPackets(WMMessagePipeline pipeline)
	{
		pipeline.registerPacket(MsgCannonFire.class);
		pipeline.registerPacket(MsgExplosion.class);
	}
	
	public void registerIcons()
	{
	}
	
	public void registerRenderers(WeaponModConfig config)
	{
	}
}
