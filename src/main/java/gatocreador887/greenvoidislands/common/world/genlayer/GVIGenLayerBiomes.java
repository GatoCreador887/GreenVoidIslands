package gatocreador887.greenvoidislands.common.world.genlayer;

import java.util.ArrayList;
import java.util.List;

import gatocreador887.greenvoidislands.GVIConfig;
import gatocreador887.greenvoidislands.common.core.GVIBiomeManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GVIGenLayerBiomes extends GenLayer {
	protected List<Biome> allowedBiomes = new ArrayList<Biome>();
	
	public GVIGenLayerBiomes(long seed) {
		super(seed);
		
		if (GVIConfig.GENERATION.biomes.plainsIsland) {
			allowedBiomes.add(GVIBiomeManager.PLAINS_ISLAND);
		}
		
		if (GVIConfig.GENERATION.biomes.barrenIsland) {
			allowedBiomes.add(GVIBiomeManager.BARREN_ISLAND);
		}
		
		if (GVIConfig.GENERATION.biomes.forestIsland) {
			allowedBiomes.add(GVIBiomeManager.FOREST_ISLAND);
		}
	}
	
	public GVIGenLayerBiomes(long seed, GenLayer genlayer) {
		this(seed);
		
		this.parent = genlayer;
	}
	
	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width * depth);
		
		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {
				this.initChunkSeed(dx + x, dz + z);
				
				dest[dx + dz * width] = Biome.getIdForBiome(this.allowedBiomes.get(this.nextInt(this.allowedBiomes.size())));
			}
		}
		
		return dest;
	}
}