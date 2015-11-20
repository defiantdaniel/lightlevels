package com.dd.lightlevels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import com.dd.lightlevels.init.ConfigHandler;

public class LLGuiPos {

	int GuiX, GuiY, Width, Height;
	Minecraft Mc;
	String Str;
	String[] Pos;

	public LLGuiPos(Minecraft Mc, String Str){
		
		this.Mc = Mc;
		this.Str = Str;
		this.Pos = ConfigHandler.GuiPos.split("[ ]+");
		
	    ScaledResolution Res = new ScaledResolution(Mc, Mc.displayWidth, Mc.displayHeight);
	    this.Width = Res.getScaledWidth();
	    this.Height = Res.getScaledHeight();		
		  
	    X();
	    Y();

	}

	private void X() {
    	if (Pos[0].toLowerCase().equals("left")) {
    		this.GuiX = ConfigHandler.GuiOffsetX;
    	} else if (Pos[0].toLowerCase().equals("center")) {
    		if (ConfigHandler.GuiOffsetX < 0) {
    			this.GuiX = ((this.Width - this.Mc.fontRenderer.getStringWidth(this.Str))/2) - Math.abs(ConfigHandler.GuiOffsetX);
    		} else {
    			this.GuiX = ((this.Width - this.Mc.fontRenderer.getStringWidth(this.Str))/2) + ConfigHandler.GuiOffsetX;
    		}
    	} else if (Pos[0].toLowerCase().equals("right")) {
    		this.GuiX = (this.Width - this.Mc.fontRenderer.getStringWidth(this.Str)) - ConfigHandler.GuiOffsetX;
     	}		
	}
	
	private void Y() {
	    if (Pos[1].toLowerCase().equals("top")) {    		    	
	    	this.GuiY = ConfigHandler.GuiOffsetY;	    	
	    } else if (Pos[1].toLowerCase().equals("center")) {    	
			if (ConfigHandler.GuiOffsetY < 0) {
				this.GuiY = (this.Height - this.Mc.fontRenderer.FONT_HEIGHT)/2 - Math.abs(ConfigHandler.GuiOffsetY);
			} else {
				this.GuiY = (this.Height - this.Mc.fontRenderer.FONT_HEIGHT)/2 + ConfigHandler.GuiOffsetY;
			}	    	
	    } else if (Pos[1].toLowerCase().equals("bottom")) {	    	
	    	this.GuiY = (this.Height - this.Mc.fontRenderer.FONT_HEIGHT) - ConfigHandler.GuiOffsetY;
	    }
	}
	
}
