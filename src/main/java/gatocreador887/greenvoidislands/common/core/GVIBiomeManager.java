package gatocreador887.greenvoidislands.common.core;

import java.util.ArrayList;
import java.util.List;

import gatocreador887.greenvoidislands.common.world.biome.BiomeBarrenIsland;
import gatocreador887.greenvoidislands.common.world.biome.BiomeForestIsland;
import gatocreador887.greenvoidislands.common.world.biome.BiomePlainsIsland;
import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class GVIBiomeManager {
	public static final List<Biome> BIOMES = new ArrayList<>();
	
	public static final Biome PLAINS_ISLAND = register(new BiomePlainsIsland(new Biome.BiomeProperties("Plains Island").setRainfall(0.0F).setRainDisabled()), new ResourceLocation(GVIReference.ID, "plains_island"));
	public static final Biome BARREN_ISLAND = register(new BiomeBarrenIsland(new Biome.BiomeProperties("Barren Island").setRainfall(0.0F).setRainDisabled()), new ResourceLocation(GVIReference.ID, "barren_island"));
	public static final Biome FOREST_ISLAND = register(new BiomeForestIsland(new Biome.BiomeProperties("Forest Island").setRainfall(0.0F).setRainDisabled()), new ResourceLocation(GVIReference.ID, "forest_island"));
	
	public static Biome register(Biome biomeIn, ResourceLocation name) {
		Biome biome = biomeIn;
		biome.setRegistryName(name);
		BIOMES.add(biome);
		
		return biome;
	}
	
	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		Biome[] array = new Biome[0];
		array = BIOMES.toArray(array);
		event.getRegistry().registerAll(array);
	}
}
