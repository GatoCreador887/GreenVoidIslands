package gatocreador887.greenvoidislands.common.core;

import gatocreador887.greenvoidislands.GreenVoidIslands;
import gatocreador887.greenvoidislands.common.entity.bird.passive.EntityShronker;
import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class GVIEntityManager {
	public static final GreenVoidIslands MOD = GreenVoidIslands.instance;
	
	public static void register() {
		int id = 0;
		
		EntityRegistry.registerModEntity(new ResourceLocation(GVIReference.ID, "shronker"), EntityShronker.class, GVIReference.ID + ":shronker", id++, MOD, 64, 1, true, 0x0E248B, 0xFFFFFF);
	}
}
