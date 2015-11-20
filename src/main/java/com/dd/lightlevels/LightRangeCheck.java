package com.dd.lightlevels;

import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.Constants.NBT;

import com.dd.lightlevels.init.ConfigHandler;
import com.dd.lightlevels.init.LightRange;

import cpw.mods.fml.common.gameevent.TickEvent;

public class LightRangeCheck {
	
	// Return var.
	boolean LightTest;
	int LightLevel;
	
	// Temp vars for values from the config.
	float TmpDmg;
	String TmpCmd;
	int TmpTimer;
	// Temp vars for the first, and second numbers of the Light Range array.
	int TmpLess;
	int TmpGreat;
	// Temp var for which position of the array it has accessed.
	int Index;

	// Returns false by default.
	public LightRangeCheck () {
		this.LightTest = false;
	}
	
	// Needs the event passed to it.
	public void setEvent(TickEvent.PlayerTickEvent event) {
		
		float y = event.player.getBrightness(1.0f);
		
		this.LightLevel = (int)Math.round(2 * (30 * y / (3 * y + 1)));
		
		// Loops through every light range in the Default Values array.
		for (LightRange Range : ConfigHandler.DefaultValues) {
			
			// Converts the high/low light range from the config, into the number that the GetBrightness call will send.
			// Has to this, because, for some reason, MC doesn't just divide by 15, it does some funky math.
			// -----------------			
			double f1 = 1.0f - ((float)Range.getLr()[0] / 15.0f);
			double f2 = 1.0f - ((float)Range.getLr()[1] / 15.0f);
			
			double LowRange = (1.0f - f1) / (f1 * 3.0f + 1.0f);
			double HighRange = (1.0f - f2) / (f2 * 3.0f + 1.0f);
			
			// -----------------
			
			/*
			System.out.println(event.player.worldObj.calculateSkylightSubtracted(event.player.getBrightness(1.0f))+"OUTSIDE");
			
			if ((event.player.getBrightness(1.0f) == 1.0f) && event.player.worldObj.canBlockSeeTheSky(MathHelper.floor_double(event.player.posX), (int)event.player.boundingBox.minY, MathHelper.floor_double(event.player.posZ))) {
				System.out.println(event.player.worldObj.calculateSkylightSubtracted(event.player.getBrightness(1.0f))+"INSIDE");
			}
			*/
			
			// Checks the high/low to make sure the player is in this light range. GetBrightness returns a max of 1.0f (light level 15).
			if ((event.player.getBrightness(1.0f) >= LowRange) &&
		        (event.player.getBrightness(1.0f) <= HighRange)) {
				// Returns true, and gets all the variables for this light range from the config.
				this.LightTest = true;
				this.TmpDmg = Range.getLrDmg();
				this.TmpCmd = Range.getLrCmd();
				this.TmpTimer = Range.getLrTimer();		    
				this.TmpLess = Range.getLr()[0];
				this.TmpGreat = Range.getLr()[1];
				this.Index = Range.getLrIndex();
				// Conditions fulfilled. Break out motha fucka.
				break;
			}
		}
	}
	
	public boolean check(){		
		return this.LightTest;
	}
	
	public float getDmg () {
		return this.TmpDmg;
	}
	
	public String getCmd () {
		return this.TmpCmd;
	}
	
	public int getTimer () {
		return this.TmpTimer;
	}
	
	public int getLrLess () {
		return this.TmpLess;
	}
	
	public int getLrGreat () {
		return this.TmpGreat;
	}	
	
	public int getIndex () {
		return this.Index;
	}	
	
	public int getLightLevel () {
		return this.LightLevel;
	}	
	
}
