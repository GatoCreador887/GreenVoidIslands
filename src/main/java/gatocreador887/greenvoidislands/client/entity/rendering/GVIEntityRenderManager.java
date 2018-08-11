package gatocreador887.greenvoidislands.client.entity.rendering;

import gatocreador887.greenvoidislands.client.entity.rendering.bird.passive.RenderShronker;
import gatocreador887.greenvoidislands.common.entity.bird.passive.EntityShronker;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class GVIEntityRenderManager {
	public static void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityShronker.class, RenderShronker.FACTORY);
	}
}
