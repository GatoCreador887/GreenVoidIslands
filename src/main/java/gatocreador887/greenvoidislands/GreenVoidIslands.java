package gatocreador887.greenvoidislands;

import org.apache.logging.log4j.Logger;

import gatocreador887.greenvoidislands.client.ClientEventHandler;
import gatocreador887.greenvoidislands.client.core.GVICreativeTabManager;
import gatocreador887.greenvoidislands.common.Proxy;
import gatocreador887.greenvoidislands.common.ServerEventHandler;
import gatocreador887.greenvoidislands.common.core.GVIBiomeManager;
import gatocreador887.greenvoidislands.common.core.GVIBlockManager;
import gatocreador887.greenvoidislands.common.core.GVIDimensionManager;
import gatocreador887.greenvoidislands.common.core.GVIEntityManager;
import gatocreador887.greenvoidislands.common.core.GVIItemManager;
import gatocreador887.greenvoidislands.common.core.GVILootTableManager;
import gatocreador887.greenvoidislands.common.core.GVIRecipeManager;
import gatocreador887.greenvoidislands.common.core.GVISoundManager;
import gatocreador887.greenvoidislands.common.world.gen.GVIWorldGen;
import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.client.audio.MusicTicker;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = GVIReference.ID, name = GVIReference.NAME, version = GVIReference.VERSION, dependencies = GVIReference.DEPENDENCIES, acceptedMinecraftVersions = GVIReference.MC_VERSIONS)
public class GreenVoidIslands {
	public static final MusicTicker.MusicType MUSIC_TYPE = EnumHelperClient.addMusicType("GVI", GVISoundManager.GVI_MUSIC, 270, 540);
	
	@Instance
	public static GreenVoidIslands instance;
	
	@SidedProxy(clientSide = GVIReference.CLIENT_PROXY_PATH, serverSide = GVIReference.SERVER_PROXY_PATH)
	public static Proxy proxy;
	
	public static Side side;
	public static Logger logger;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		side = event.getSide();
		logger = event.getModLog();
		
		GVIEntityManager.register();
		GVIRecipeManager.init();
		GVILootTableManager.registerAll();
		
		MinecraftForge.EVENT_BUS.register(GVIBlockManager.class);
		MinecraftForge.EVENT_BUS.register(GVIItemManager.class);
		MinecraftForge.EVENT_BUS.register(GVIBiomeManager.class);
		MinecraftForge.EVENT_BUS.register(GVISoundManager.class);
		MinecraftForge.EVENT_BUS.register(GreenVoidIslands.class);
		MinecraftForge.EVENT_BUS.register(ServerEventHandler.class);
		MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
		MinecraftForge.EVENT_BUS.register(GVITickHandler.class);
		
		proxy.registerEntityRenders();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		
		GameRegistry.registerWorldGenerator(new GVIWorldGen(), 0);
		
		GVIDimensionManager.init(event);
		
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		
		proxy.registerRenders();
		
	}
	
}
