package gatocreador887.greenvoidislands.client.core;

import gatocreador887.greenvoidislands.client.creativetab.GearTab;
import gatocreador887.greenvoidislands.client.creativetab.MaterialsTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GVICreativeTabManager {
	
	public static final CreativeTabs MATERIALS = new MaterialsTab("gvi_materials");
	public static final CreativeTabs GEAR = new GearTab("gvi_gear");
	
}
