package com.dd.lightlevels.init;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	
	// Default the Dims list to a blacklist.
	public static boolean DimsWhiteList = false;
	// The dimension whitelist/blacklist.
	public static int[] Dims = {};
	// Turns the mod off in water. Will not reduce timers set before getting in water. Basically pauses the mod.
	public static boolean WaterFlag = false;
	
	// Sets the defaults for Light Ranges, Damage, Command, Timer, and the index.
	public static LightRange[] DefaultValues = new LightRange[] {
			  new LightRange(new int[] {0,5}, 1.0f, "", 10, 1),
			  new LightRange(new int[] {5,7}, 1.0f, "", -1, 2),
			  new LightRange(new int[] {7,9}, 1.0f, "", -1, 3),
			  new LightRange(new int[] {9,11}, 1.0f, "", -1, 4),
			  new LightRange(new int[] {11,14}, 1.0f, "", -1, 5)
	};
	
	public static void ConfigSetup (FMLPreInitializationEvent event) {
	
		// Creates a new config at the suggested location.
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		// Loads the config. I think you could have figured that one out, but here's a comment just in-case.
		config.load();
		
		// Sets whitelist, and dims.
		DimsWhiteList = config.get(Configuration.CATEGORY_GENERAL, "DimsWhiteList", DimsWhiteList, "If true, vars in the \"Dims\" list will act as a white list. Otherwise dimension ids put there will turn the mod off in those dimensions. [Default = false]").getBoolean(DimsWhiteList);
		Dims = config.get(Configuration.CATEGORY_GENERAL, "Dims", Dims, "Dimensions defined by id (ex. Overworld is 0) that this mod will act (or not act) in depending on whether it's a whitelist or blacklist.").getIntList();
		
		// Sets Water flag
		WaterFlag = config.get(Configuration.CATEGORY_GENERAL, "WaterFlag", WaterFlag, "If true, the mod will pause when the player enters water. Otherwise the mod will keep tracking light levels as normal. So the player will need to place light sources. [Default = false]").getBoolean(WaterFlag);		
		
		for (int i = 1; i < 6; i++) {
			
			// Defines the category header for this light range.
			String LightHeader = "Light " + i;
			
			// System.out.println(DefaultValues[i-1].getLrCmd()); // Debug code.
			
			// Sets light range, dmg, command, and timer based on default values array.
			int[] LightRangeTmp = config.get(LightHeader, ("LightRange" + i), DefaultValues[i-1].getLr(), ("The items in \"light " + i + "\" will effect the player only in this light range. The mod will take effect when the players light level is greater than the first number, and less than the second (MUST be smaller number first, and greater number second). You should be careful overlaping these. [Accepted values 0-15. Anything else will break the mod]")).getIntList();
			float LightRangeDmgTmp = (float)config.get(LightHeader, ("LightRangeDmg" + i), DefaultValues[i-1].getLrDmg(), ("Damage to do to the player. [Default = 1.0]")).getDouble(DefaultValues[i-1].getLrDmg());
			String LightRangeCmdTmp = config.get(LightHeader, ("LightRangeCmd" + i), DefaultValues[i-1].getLrCmd(), ("Command line command to execute.")).getString(); 
			int LightRangeTimerTmp = config.get(LightHeader, ("LightRangeTimer" + i), DefaultValues[i-1].getLrTimer(), ("The amount of time (in Seconds) the player must spend in this light level to initiate an action. (Set to -1 to disable actions for this light level)")).getInt(DefaultValues[i-1].getLrTimer());
			
			// Puts the current config values back into the default values array, so it can be used elsewhere.
			DefaultValues[i-1].setLr(LightRangeTmp);
			DefaultValues[i-1].setLrDmg(LightRangeDmgTmp);
			DefaultValues[i-1].setLrCmd(LightRangeCmdTmp);
			DefaultValues[i-1].setLrTimer(LightRangeTimerTmp);
			DefaultValues[i-1].setLrIndex(i);
			
			// System.out.println(DefaultValues[i-1].getLrCmd()); // Debug code.
		}
		
		// Invokes the spirit of Christopher Walken inside your computer. He's gonna shoot you.
		config.save();
		
	}
}
