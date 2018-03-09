package creepersgalore.greenvoidislands.client;

import creepersgalore.greenvoidislands.common.ServerProxy;
import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import creepersgalore.greenvoidislands.common.core.GVIItemManager;

public class ClientProxy extends ServerProxy {
	
	public void registerRenders() {
		
		GVIItemManager.registerRenders();
		GVIBlockManager.registerRenders();
		
	}
	
}
