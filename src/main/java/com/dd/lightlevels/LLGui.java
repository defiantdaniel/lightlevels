package com.dd.lightlevels;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.dd.lightlevels.init.ConfigHandler;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class LLGui extends Gui {
	private Minecraft Mc;
	ResourceLocation LLGuiRes = new ResourceLocation("lightlevels", "textures/gui/lightlevelsgui.png");
	int x, y;
	
	public LLGui(Minecraft Mc) {
		super();
		this.Mc = Mc;	    		
	}
	

	  @SubscribeEvent
	  @SideOnly(Side.CLIENT)
	  public void onRenderExperienceBar(RenderGameOverlayEvent event)
	  {

	    if(event.isCancelable() || event.type != ElementType.EXPERIENCE)
	    {      
	      return;
	    }

	    // Starting position for the buff bar - 2 pixels from the top left corner.
	    int xPos = 2;
	    int yPos = 2;

	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glDisable(GL11.GL_LIGHTING);
	    
	    FontRenderer FontRender = this.Mc.fontRenderer;
	    
	    String Str;
	    
	    if (TickEventHandler.Timer == null) {
	    	Str = "Light Level: " + TickEventHandler.GuiLightLevel + " Timer: 0/0";
	    } else {
	    	Str = "Light Level: " + TickEventHandler.GuiLightLevel + " Timer: " + TickEventHandler.Timer;	    	
	    }
	    
	    LLGuiPos Pos = new LLGuiPos(this.Mc, Str);
	    
	    if (ConfigHandler.GuiType == 1) {
	    	drawString(this.Mc.fontRenderer, Str, Pos.GuiX, Pos.GuiY, 0xFFAA00);
	    } else if (ConfigHandler.GuiType == 2) {
	    	drawRect(2, 14, 22, 2, 0xFF000000);
	    	int length = Math.round(((float)TickEventHandler.GuiLightLevel / 15f) * 20f);
	    	if (length < 3) {length = 3;}
			// System.out.println("Gui Light Level: " + TickEventHandler.GuiLightLevel + " Divided: " + (TickEventHandler.GuiLightLevel / 15) + " Multiplied: " + length); // Debug Code.	    	
	    	drawRect(3, 12, length, 3, 0xFFAA0000);
	    	
	    	this.Mc.renderEngine.bindTexture(LLGuiRes);
	    	this.drawTexturedModalRect(2, 2, 0, 0, 16, 16);
	    }
	    
	  }

}
