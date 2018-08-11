package gatocreador887.greenvoidislands.common.core;

import java.util.ArrayList;
import java.util.List;

import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class GVILootTableManager {
	public static final List<ResourceLocation> LOOT_TABLES = new ArrayList<>();
	
	public static final ResourceLocation SHRONKER = register(new ResourceLocation(GVIReference.ID, "entities/shronker"));
	
	public static ResourceLocation register(ResourceLocation name) {
		LOOT_TABLES.add(name);
		
		return name;
	}
	
	public static void registerAll() {
		for (ResourceLocation table : LOOT_TABLES) {
			LootTableList.register(table);
		}
	}
}
