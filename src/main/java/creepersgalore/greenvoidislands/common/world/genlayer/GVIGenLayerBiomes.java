package creepersgalore.greenvoidislands.common.world.genlayer;

import creepersgalore.greenvoidislands.common.core.GVIBiomeManager;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GVIGenLayerBiomes extends GenLayer {
		
	protected Biome[] allowedBiomes = {GVIBiomeManager.barrenIsland, GVIBiomeManager.plainsIsland, GVIBiomeManager.forestIsland};
		
	public GVIGenLayerBiomes(long seed) {
		super(seed);
		
		
		
	}
	
	public GVIGenLayerBiomes(long seed, GenLayer genlayer) {
		super(seed);
		
		this.parent = genlayer;
		
	}
	
	public int[] getInts(int x, int z, int width, int depth) {
		
		int[] dest = IntCache.getIntCache(width * depth);
		
		for (int dz = 0; dz < depth; dz++) {
			
			for (int dx = 0; dx < width; dx++) {
			
			this.initChunkSeed(dx + x, dz + z);
			
			dest[(dx + dz * width)] = Biome.getIdForBiome(this.allowedBiomes[nextInt(this.allowedBiomes.length)]);
			
			}
			
		}
		
		return dest;
		
	}
	
}