package creepersgalore.greenvoidislands.common.core;

import creepersgalore.greenvoidislands.common.world.biome.BiomeBarrenIsland;
import creepersgalore.greenvoidislands.common.world.biome.BiomeForestIsland;
import creepersgalore.greenvoidislands.common.world.biome.BiomePlainsIsland;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class GVIBiomeManager {
	
	public static Biome barrenIsland;
	public static Biome plainsIsland;
	public static Biome forestIsland;
	
	public static void preInit(FMLPreInitializationEvent event) {
		
		barrenIsland = new BiomeBarrenIsland(new Biome.BiomeProperties("Barren Island").setRainfall(0.0F).setRainDisabled()).setRegistryName("barren_island");
		plainsIsland = new BiomePlainsIsland(new Biome.BiomeProperties("Plains Island").setRainfall(0.0F).setRainDisabled()).setRegistryName("plains_island");
		forestIsland = new BiomeForestIsland(new Biome.BiomeProperties("Forest Island").setRainfall(0.0F).setRainDisabled()).setRegistryName("forest_island");
		
		ForgeRegistries.BIOMES.register(barrenIsland);
		ForgeRegistries.BIOMES.register(plainsIsland);
		ForgeRegistries.BIOMES.register(forestIsland);
		
	}
	
}
