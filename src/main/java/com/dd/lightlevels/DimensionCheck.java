package com.dd.lightlevels;

import com.dd.lightlevels.init.ConfigHandler;

import cpw.mods.fml.common.gameevent.TickEvent;

public class DimensionCheck {
	
	// Return variable.
	boolean DimsTest;

	// Init, sets return to true by default.
	public DimensionCheck () {
		this.DimsTest = true;
	}
	
	// Takes a player tickevent.
	public void setEvent(TickEvent.PlayerTickEvent event) {
		// Loops through all the dimensions listed in the config.
		for (int dimID : ConfigHandler.Dims) {
			// Checks if the dims list is meant to be blacklist, or whitelist. Then checks if the current dimension equals/notequals
			// one in the config.
			if ((!ConfigHandler.DimsWhiteList && event.player.dimension == dimID) ||
		        (ConfigHandler.DimsWhiteList && event.player.dimension != dimID)) {
				this.DimsTest = false;
				break;
			}
		}
	}
	
	public boolean check(){		
		return this.DimsTest;
	}
	
}
