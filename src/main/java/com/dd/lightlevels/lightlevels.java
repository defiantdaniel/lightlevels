package com.dd.lightlevels;

import com.dd.lightlevels.init.llBlocks;
import com.dd.lightlevels.init.llItems;
import com.dd.lightlevels.ref.ModInfo;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.Name)
public class lightlevels {
	
	@Instance
	public static lightlevels instance;
	
	@SidedProxy (serverSide = "com.dd.lightlevels.CommonProxy", clientSide = "com.dd.lightlevels.client.ClientProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		llBlocks.initialize();
		llBlocks.register();
		
		llItems.initialize();
		llItems.register();
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}	
}
