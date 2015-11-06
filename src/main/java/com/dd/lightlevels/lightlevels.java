package com.dd.lightlevels;

import net.minecraftforge.common.config.Configuration;

import com.dd.lightlevels.init.ConfigHandler;
import com.dd.lightlevels.init.LLBlocks;
import com.dd.lightlevels.init.LLItems;
import com.dd.lightlevels.ref.ModInfo;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.Name, version = ModInfo.Version)
public class LightLevels {
	
	@Instance
	public static LightLevels instance;
	
	@SidedProxy (serverSide = "com.dd.lightlevels.CommonProxy", clientSide = "com.dd.lightlevels.client.ClientProxy")
	public static CommonProxy proxy;
	
	public static Configuration config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		ConfigHandler.ConfigSetup(event);
		
		LLBlocks.initialize();
		LLBlocks.register();
		
		LLItems.initialize();
		LLItems.register();
		
		FMLCommonHandler.instance().bus().register(new TickEventHandler());
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}	
}
