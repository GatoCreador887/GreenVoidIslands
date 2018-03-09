package creepersgalore.greenvoidislands;

import net.minecraft.client.audio.MusicTicker;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import creepersgalore.greenvoidislands.client.ClientEventHandler;
import creepersgalore.greenvoidislands.client.core.GVICreativeTabManager;
import creepersgalore.greenvoidislands.common.ServerEventHandler;
import creepersgalore.greenvoidislands.common.ServerProxy;
import creepersgalore.greenvoidislands.common.core.GVIBiomeManager;
import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import creepersgalore.greenvoidislands.common.core.GVIDimensionManager;
import creepersgalore.greenvoidislands.common.core.GVIItemManager;
import creepersgalore.greenvoidislands.common.core.GVIRecipeManager;
import creepersgalore.greenvoidislands.common.core.GVISoundManager;
import creepersgalore.greenvoidislands.common.world.gen.GVIWorldGen;
import creepersgalore.greenvoidislands.util.Reference;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, acceptedMinecraftVersions = Reference.MC_VERSIONS)
public class GreenVoidIslands {
	
	public static final MusicTicker.MusicType MUSIC_TYPE = EnumHelperClient.addMusicType("GVI", GVISoundManager.gvi_music, 270, 540);
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_PATH, serverSide = Reference.SERVER_PROXY_PATH)
	public static ServerProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		
		GVICreativeTabManager.preInit(event);
		GVIBlockManager.preInit(event);
		GVIItemManager.preInit(event);
		GVISoundManager.preInit(event);
		GVIRecipeManager.preInit(event);
		GVIBiomeManager.preInit(event);
		
		MinecraftForge.EVENT_BUS.register(GreenVoidIslands.class);
		MinecraftForge.EVENT_BUS.register(ServerEventHandler.class);
		MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
		MinecraftForge.EVENT_BUS.register(GVITickHandler.class);
		
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
