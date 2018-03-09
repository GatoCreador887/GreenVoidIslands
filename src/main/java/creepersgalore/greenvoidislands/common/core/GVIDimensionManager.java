package creepersgalore.greenvoidislands.common.core;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import creepersgalore.greenvoidislands.common.world.GVIWorldProvider;

public class GVIDimensionManager {
	
	public static DimensionType dtype = DimensionType.register("Green Void Islands", "green_void_islands", 7, GVIWorldProvider.class, false);
	
	public static void init(FMLInitializationEvent event) {
		
		DimensionManager.registerDimension(7, dtype);
		
	}
	
}
