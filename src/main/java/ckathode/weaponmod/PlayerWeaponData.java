package ckathode.weaponmod;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;

public abstract class PlayerWeaponData
{
	public static final int	BOOLEANS	= 26, WARHAMMER_LAST_SMASH_TICKS = 27, FLAIL_ENTITY_ID = 28;
	public static final int	WARHAMMER_CHARGED	= 1, FLAIL_THROWN = 2;
	
	public static void initPlayerWeaponData(EntityPlayer player)
	{
		String playername = getPlayerName(player);
		BalkonsWeaponMod.modLog.trace("Initializing DataWatcher values for " + playername);
		DataWatcher datawatcher = player.getDataWatcher();
		try
		{
			datawatcher.getWatchableObjectInt(BOOLEANS);
			BalkonsWeaponMod.modLog.warn("DataWatcher ID conflict for " + playername + " @ " + BOOLEANS);
		} catch (NullPointerException e)
		{} finally
		{
			datawatcher.addObject(BOOLEANS, Integer.valueOf(0));
		}
		
		try
		{
			datawatcher.getWatchableObjectInt(WARHAMMER_LAST_SMASH_TICKS);
			BalkonsWeaponMod.modLog.warn("DataWatcher ID conflict for " + playername + " @ " + WARHAMMER_LAST_SMASH_TICKS);
		} catch (NullPointerException e)
		{} finally
		{
			datawatcher.addObject(WARHAMMER_LAST_SMASH_TICKS, Integer.valueOf(player.ticksExisted));
		}
		
		try
		{
			datawatcher.getWatchableObjectInt(FLAIL_ENTITY_ID);
			BalkonsWeaponMod.modLog.warn("DataWatcher ID conflict for " + playername + " @ " + FLAIL_ENTITY_ID);
		} catch (NullPointerException e)
		{} finally
		{
			datawatcher.addObject(FLAIL_ENTITY_ID, Integer.valueOf(0));
		}
	}
	
	private static String getPlayerName(EntityPlayer player)
	{
		String playername;
		if (player.getGameProfile() != null)
		{
			playername = player.getCommandSenderName();
		} else
		{
			playername = "[unknown]";
		}
		playername = "player:" + playername;
		return playername;
	}
	
	private static void unavailableError(EntityPlayer player, int id)
	{
		BalkonsWeaponMod.modLog.error("DataWatcher ID " + id + " for " + getPlayerName(player) + " unavailable, trying to reinitialize");
		initPlayerWeaponData(player);
	}
	
	public static void setFlailEntityId(EntityPlayer player, int id)
	{
		try
		{
			player.getDataWatcher().updateObject(FLAIL_ENTITY_ID, Integer.valueOf(id));
		} catch (NullPointerException e)
		{
			unavailableError(player, FLAIL_ENTITY_ID);
		}
	}
	
	public static int getFlailEntityId(EntityPlayer player)
	{
		try
		{
			return player.getDataWatcher().getWatchableObjectInt(FLAIL_ENTITY_ID);
		} catch (NullPointerException e)
		{
			unavailableError(player, FLAIL_ENTITY_ID);
		}
		return 0;
	}
	
	public static int getLastWarhammerSmashTicks(EntityPlayer player)
	{
		try
		{
			return player.getDataWatcher().getWatchableObjectInt(WARHAMMER_LAST_SMASH_TICKS);
		} catch (NullPointerException e)
		{
			unavailableError(player, WARHAMMER_LAST_SMASH_TICKS);
		}
		return 0;
	}
	
	public static void setLastWarhammerSmashTicks(EntityPlayer player, int age)
	{
		try
		{
			player.getDataWatcher().updateObject(WARHAMMER_LAST_SMASH_TICKS, age);
		} catch (NullPointerException e)
		{
			unavailableError(player, WARHAMMER_LAST_SMASH_TICKS);
		}
	}
	
	public static void setFlailThrown(EntityPlayer player, boolean flag)
	{
		setBoolean(player, FLAIL_THROWN, flag);
	}
	
	public static boolean isFlailThrown(EntityPlayer player)
	{
		return getBoolean(player.getDataWatcher().getWatchableObjectInt(BOOLEANS), FLAIL_THROWN);
	}
	
	public static void setBoolean(EntityPlayer player, int state, boolean flag)
	{
		try
		{
			int i = player.getDataWatcher().getWatchableObjectInt(BOOLEANS);
			i = setBoolean(i, state, flag);
			player.getDataWatcher().updateObject(BOOLEANS, i);
		} catch (NullPointerException e)
		{
			unavailableError(player, BOOLEANS);
		}
	}
	
	public static boolean getBoolean(EntityPlayer player, int state)
	{
		try
		{
			return getBoolean(player.getDataWatcher().getWatchableObjectInt(BOOLEANS), state);
		} catch (NullPointerException e)
		{
			unavailableError(player, BOOLEANS);
		}
		return false;
	}
	
	private static boolean getBoolean(int i, int pos)
	{
		return (i & (1 << pos)) != 0;
	}
	
	private static int setBoolean(int i, int pos, boolean flag)
	{
		int mask = (1 << pos);
		i &= ~mask;
		if (flag)
		{
			i |= mask;
		}
		return i;
	}
	
	/*
	private static byte getByte(int i, int pos)
	{
		pos <<= 2;
		return (byte) ((i >>> pos) & 0xFF);
	}
	
	private static int setByte(int i, int pos, byte b)
	{
		//TODO
		return i;
	}
	*/
}
