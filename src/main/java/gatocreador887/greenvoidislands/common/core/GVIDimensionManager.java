package gatocreador887.greenvoidislands.common.core;

import gatocreador887.greenvoidislands.GVIConfig;
import gatocreador887.greenvoidislands.common.world.GVIWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class GVIDimensionManager {
	
	public static DimensionType dtype = DimensionType.register("green_void_islands", "_green_void_islands", GVIConfig.dimensionId, GVIWorldProvider.class, false);
	
	public static void init(FMLInitializationEvent event) {
		
		DimensionManager.registerDimension(GVIConfig.dimensionId, dtype);
		
	}
	
}
