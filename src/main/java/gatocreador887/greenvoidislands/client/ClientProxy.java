package gatocreador887.greenvoidislands.client;

import gatocreador887.greenvoidislands.client.entity.rendering.GVIEntityRenderManager;
import gatocreador887.greenvoidislands.common.Proxy;
import gatocreador887.greenvoidislands.common.core.GVIBlockManager;
import gatocreador887.greenvoidislands.common.core.GVIItemManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends Proxy {
	@Override
	public void registerEntityRenders() {
		GVIEntityRenderManager.registerRenders();
	}
	
	@Override
	public void registerRenders() {
		GVIItemManager.registerRenders();
		GVIBlockManager.registerRenders();
	}
}
