package gatocreador887.greenvoidislands.common.world.biome;

import java.util.Random;

import gatocreador887.greenvoidislands.common.block.BlockTallIslandGrass;
import gatocreador887.greenvoidislands.common.core.GVIBlockManager;
import gatocreador887.greenvoidislands.common.entity.bird.passive.EntityShronker;
import gatocreador887.greenvoidislands.common.world.gen.feature.WorldGenTallIslandGrass;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeForestIsland extends BiomeGVI {
	
	public BiomeForestIsland(BiomeProperties properties) {
		super(properties);
		
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCreatureList.add(new SpawnListEntry(EntityShronker.class, 70, 2, 4));
		this.topBlock = GVIBlockManager.ISLAND_GRASS.getDefaultState();
		this.decorator.treesPerChunk = 10;
		this.decorator.grassPerChunk = 2;
		this.flowers.clear();
		
	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		
		return (WorldGenAbstractTree) (rand.nextInt(9) == 0 ? SHRONK_TREE_FEATURE : TREE_FEATURE);
		
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand) {
		
		return new WorldGenTallIslandGrass(BlockTallIslandGrass.EnumType.GRASS);
		
	}
	
}
