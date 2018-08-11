package gatocreador887.greenvoidislands.common.world.biome;

import java.util.Random;

import gatocreador887.greenvoidislands.common.block.BlockTallIslandGrass;
import gatocreador887.greenvoidislands.common.core.GVIBlockManager;
import gatocreador887.greenvoidislands.common.world.gen.feature.WorldGenTallIslandGrass;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeBarrenIsland extends BiomeGVI {
	public BiomeBarrenIsland(BiomeProperties properties) {
		super(properties);
		
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.topBlock = GVIBlockManager.DEAD_ISLAND_GRASS.getDefaultState();
		this.decorator = new BiomeBarrenIslandDecorator();
		this.decorator.grassPerChunk = 4;
		this.flowers.clear();
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand) {
		return new WorldGenTallIslandGrass(BlockTallIslandGrass.EnumType.DEAD_GRASS);
	}
	
	public static class BiomeBarrenIslandDecorator extends BiomeDecorator {
		@Override
		public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
			if (this.decorating) {
				throw new RuntimeException("Already decorating");
			} else {
				this.chunkPos = pos;
				
				if (TerrainGen.decorate(worldIn, random, this.chunkPos, DecorateBiomeEvent.Decorate.EventType.GRASS)) {
					for (int i3 = 0; i3 < this.grassPerChunk; ++i3) {
						int j7 = random.nextInt(16) + 8;
						int i11 = random.nextInt(16) + 8;
						int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;
						
						if (k14 > 0) {
							int l17 = random.nextInt(k14);
							biome.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
						}
					}
				}
				
				this.decorating = false;
			}
		}
	}
}
