package creepersgalore.greenvoidislands.client.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import creepersgalore.greenvoidislands.client.creativetab.GearTab;
import creepersgalore.greenvoidislands.client.creativetab.MaterialsTab;

public class GVICreativeTabManager {
	
	public static CreativeTabs materials;
	public static CreativeTabs gear;
	
	public static void preInit(FMLPreInitializationEvent event) {
		
		materials  = new MaterialsTab("gvi_materials");
		gear = new GearTab("gvi_gear");
		
	}
	
}
