package ckathode.weaponmod.network;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class WMMessagePipeline extends SimpleNetworkWrapper{
    public WMMessagePipeline(){
        super("WeaponMod");
    };
}
