package creepersgalore.greenvoidislands.common.world.biome;

import java.util.Random;

import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class BiomeBarrenIsland extends BiomeGVI {
	
	public BiomeBarrenIsland(BiomeProperties properties) {
		super(properties);
		
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.topBlock = GVIBlockManager.dead_island_grass.getDefaultState();
		this.decorator = new BiomeBarrenIslandDecorator();
		this.flowers.clear();
		
	}
	
	public static class BiomeBarrenIslandDecorator extends BiomeDecorator {
		
		public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
			
			if (this.decorating) {
				
				throw new RuntimeException("Already decorating");
				
			} else {
				
				this.decorating = false;
				
			}
			
		}
		
	}
	
}
