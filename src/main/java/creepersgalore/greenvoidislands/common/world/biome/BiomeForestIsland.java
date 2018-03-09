package creepersgalore.greenvoidislands.common.world.biome;

import java.util.Random;

import creepersgalore.greenvoidislands.common.block.BlockDoubleTallIslandGrass;
import creepersgalore.greenvoidislands.common.block.BlockTallIslandGrass;
import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import creepersgalore.greenvoidislands.common.world.gen.feature.WorldGenTallIslandGrass;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeForestIsland extends BiomeGVI {
	
	public BiomeForestIsland(BiomeProperties properties) {
		super(properties);
		
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.topBlock = GVIBlockManager.island_grass.getDefaultState();
		this.decorator.treesPerChunk = 10;
        this.decorator.grassPerChunk = 2;
		this.flowers.clear();
		
	}
	
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		
		return (WorldGenAbstractTree) (rand.nextInt(9) == 0 ? SHRONK_TREE_FEATURE : TREE_FEATURE);
		
	}
	
	public WorldGenerator getRandomWorldGenForGrass(Random rand) {
		
		return new WorldGenTallIslandGrass(BlockTallIslandGrass.EnumType.GRASS);
		
	}
	
}
