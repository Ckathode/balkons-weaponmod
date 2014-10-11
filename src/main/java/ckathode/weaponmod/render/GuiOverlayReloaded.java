package ckathode.weaponmod.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOverlayReloaded extends Gui
{
	private Minecraft	mc;
	
	public GuiOverlayReloaded(Minecraft minecraft)
	{
		super();
		mc = minecraft;
	}
	
	@SubscribeEvent
	public void renderGUIOverlay(RenderGameOverlayEvent e)
	{
		if (e instanceof RenderGameOverlayEvent.Post || e.type != ElementType.HOTBAR)
		{
			return;
		}
		
		EntityPlayer p = mc.thePlayer;
		if (p != null)
		{
			ItemStack is = p.getCurrentEquippedItem();
			if (is != null && is.getItem() instanceof IItemWeapon && ((IItemWeapon) is.getItem()).getRangedComponent() != null)
			{
				RangedComponent rc = ((IItemWeapon) is.getItem()).getRangedComponent();
				boolean rld = RangedComponent.isReloaded(is);
				//RangedComponent rc = ((IItemWeapon) is.getItem()).getRangedComponent();
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				float f;
				
				int color;
				if (rld)
				{
					f = 1F;
					if (p.getItemInUse() == is && RangedComponent.isReadyToFire(is))
					{
						color = 0x60C60000;
					} else
					{
						color = 0x60348E00;
					}
				} else if (p.getItemInUse() == is)
				{
					f = Math.min((float) p.getItemInUseDuration() / rc.getReloadDuration(is), 1F);
					color = 0x60EAA800;
				} else
				{
					f = 0F;
					color = 0;
				}
				
				int i;
				for (i = 0; i < 9; i++)
				{
					if (p.inventory.getStackInSlot(i) == is)
					{
						break;
					}
				}
				
				int x0 = e.resolution.getScaledWidth() / 2 - 88 + i * 20;
				int y0 = e.resolution.getScaledHeight() - 3;
				drawRect(x0, y0, x0 + 16, y0 - (int) (f * 16), color);
			}
		}
	}
}
