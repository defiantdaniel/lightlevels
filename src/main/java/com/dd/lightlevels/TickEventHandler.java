package com.dd.lightlevels;

import com.dd.lightlevels.init.ConfigHandler;
import com.dd.lightlevels.init.LightRange;

import net.minecraft.command.ICommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.FakePlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TickEventHandler {

	// Timer for TickEvent loop. Limits TickEvent actions to 20tics (1second).
	private int Count = 0;
	
	// Sets up the the source for damage to players as "LightLevel". This only shows the source, it isn't the actual damage.
	public static DamageSource LightLevel = (new DamageSource("LightLevel"))
			.setDamageBypassesArmor();
	
	public static String Timer;
	public static int GuiLightLevel;

	@SubscribeEvent
	public void LightLevelTick(TickEvent.PlayerTickEvent event) {

		// The set of conditions required to proceed into taking action.
		boolean Start;					
		
		// Checks conditions, sets start var.
		if ((event.phase == TickEvent.Phase.START)
			&& (!event.player.worldObj.isRemote)
			&& (event.player instanceof EntityPlayerMP)
			&& !(ConfigHandler.WaterFlag && event.player.isInWater())) {
			Start = true;
		} else {
			Start = false;
		}
		
		// Checks if the tick event is a starting one, if the event is from the server (not remote), if it's from a real player (not a fake one like spikes, etc),
		// if the player is in water (matching the flag from config), and if the Count (number of ticks that fulfill this criteria) has gone by 20 times (1 second).
		if (Start && Count == 20) {
			
			// Calls the Dimension Check class, which returns true if the player is in a dimension the mod is supposed to work in.
			DimensionCheck CorrectDim = new DimensionCheck();
			CorrectDim.setEvent(event);
			// Calls the Light Range Check class, which checks the current light level against the player's, and gets the config file
			// variables for that light level range.
			LightRangeCheck CorrectLr = new LightRangeCheck();
			CorrectLr.setEvent(event);
			// This is the string to define the timer variable stored on the player entity for this specific light range. 
			String EntityTimer = ("LightTimer" + CorrectLr.getIndex());
			// Gets the MC server, command manager so it can execute commands from the config later.
			MinecraftServer Server = MinecraftServer.getServer();
			ICommandManager CmdMng = Server.getCommandManager();
			// Sets count to 0 because if it enters here, 20 ticks have passed.
			Count = 0;

			// System.out.println("In Water = "+(event.player.isInWater() == ConfigHandler.WaterFlag)+ " " + event.player.getBrightness(1.0f)+"<< Bright -- Made it!"); // Debug Line
			// System.out.println(event.player.worldObj.calculateSkylightSubtracted(event.player.getBrightness(1.0f))+"OUTSIDE"); // Debug Line.
			
			// System.out.println(CorrectDim.check() + "<Dim --- Lr> "
			//		+ CorrectLr.check()); // Debug line

			// Checks if the player is in a Dimension, and Light Range, that is meant to perform an action by the config file.
			// Also checks the timer in the config for this light level, and if it is -1, actions for this light level are disabled.
			if ((CorrectDim.check()) && (CorrectLr.check())
					&& (CorrectLr.getTimer() != -1)) {
				
				// Gets the LightTimer for this light level off of the player entity.
				int LightTimer = event.player.getEntityData().getInteger(
						EntityTimer);
				
				Timer = LightTimer + "/" + CorrectLr.getTimer();
				GuiLightLevel = CorrectLr.getLightLevel();
				
				// Checks if the current LightTimer is equal to the the one in the config (ie. is the time up).
				if (LightTimer >= CorrectLr.getTimer()) {
					// Applies damage specified for this light level in the config, to the player.
					event.player.attackEntityFrom(LightLevel,
							CorrectLr.getDmg());
					// Executes the command for this light level from the config.
					if (!CorrectLr.getCmd().isEmpty()) {
						CmdMng.executeCommand(Server, CorrectLr.getCmd());
					}
					// To correct the timer if a lower value was set in the config.
					event.player.getEntityData().setInteger(
							"LightTimer" + CorrectLr.getIndex(),
							CorrectLr.getTimer());					
				} else {			
					if (LightTimer >= 0) {
						// Sets the LightTimer on the player for this light level to +1.
						event.player.getEntityData().setInteger(EntityTimer,
								LightTimer + 1);
					}
				}
				// Goes through each LightRange in the config reduce the timers on light ranges the player isn't in.
				LightRangeEntity_TimerReduction(event, CorrectLr.getIndex());
			} else {
				if (CorrectLr.getTimer() == -1) {
					Timer = "0/0";
				}
				GuiLightLevel = CorrectLr.getLightLevel();
				// Goes through each LightRange in the config reduce the timers on light ranges the player isn't in.
				LightRangeEntity_TimerReduction(event, CorrectLr.getIndex());
			}

		} else {
			// Checks if it's tick start, remote, not in water (with water flag set true), and a player before increasing the tick count.
			if (Start) {
				Count++;
			}
		}

	}

	private void LightRangeEntity_TimerReduction(PlayerTickEvent event, int index) {

		for (LightRange Range : ConfigHandler.DefaultValues) {
			// Gets the LightTimer for the light range of this loop iteration, off the player.
			int TmpLightTime = event.player.getEntityData().getInteger(
					"LightTimer" + Range.getLrIndex());
			// Checks if the LightRange it's working with, is NOT the one the player is currently in.
			// Also checks if the LightTimer for this light range is greater than 0 (so it doesn't go negative).
			if ((Range.getLrIndex() != index)
					&& TmpLightTime > 0) {
				// Subtracts 1 from the LightTimer for this light level.
				event.player.getEntityData().setInteger(
						"LightTimer" + Range.getLrIndex(),
						TmpLightTime - 1);
			}
			//System.out.println(event.player.getEntityData().getInteger(
			//		"LightTimer" + Range.getLrIndex())
			//		+ " INSIDE CORRECT DIM/LR " + Range.getLr()[0]); // Debug Code.
		}		
		
	}

}