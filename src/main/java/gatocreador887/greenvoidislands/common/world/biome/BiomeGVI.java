package gatocreador887.greenvoidislands.common.world.biome;

import java.util.Random;

import gatocreador887.greenvoidislands.common.core.GVIBlockManager;
import gatocreador887.greenvoidislands.common.world.gen.feature.WorldGenDoubleTallIslandGrass;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGVI extends Biome {
	
	protected static final WorldGenDoubleTallIslandGrass DOUBLE_TALL_ISLAND_GRASS_GENERATOR = new WorldGenDoubleTallIslandGrass();
	
	/** The tree generator. */
	protected static final WorldGenTrees SHRONK_TREE_FEATURE = new WorldGenTrees(false, 7, GVIBlockManager.SHRONK_LOG.getDefaultState(), GVIBlockManager.SHRONK_LEAVES.getDefaultState(), false);
	/*
	 * The big tree generator. protected static final WorldGenBigTree
	 * BIG_TREE_FEATURE = new WorldGenBigTree(false);
	 */
	
	public BiomeGVI(BiomeProperties properties) {
		super(properties);
		
		this.decorator = new BiomeGVIDecorator();
		
	}
	
	public static class BiomeGVIDecorator extends BiomeDecorator {
		
		public WorldGenerator foligenuGen;
		public WorldGenerator floatstoneShardDirtGen;
		public WorldGenerator floatstoneShardGen;
		
		@Override
		public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
			
			if (this.decorating) {
				
				throw new RuntimeException("Already decorating");
				
			} else {
				
				this.chunkPos = pos;
				this.foligenuGen = new WorldGenMinable(GVIBlockManager.FOLIGENU_ORE.getDefaultState(), 7);
				this.floatstoneShardDirtGen = new WorldGenMinable(GVIBlockManager.FLOATSTONE_SHARD_DIRT_ORE.getDefaultState(), 5, BlockMatcher.forBlock(Blocks.DIRT));
				this.floatstoneShardGen = new WorldGenMinable(GVIBlockManager.FLOATSTONE_SHARD_ORE.getDefaultState(), 5);
				this.genDecorations(biome, worldIn, random);
				this.decorating = false;
				
			}
			
		}
		
		@Override
		protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
			
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, this.chunkPos));
			
			this.generateOres(worldIn, random);
			
			int k1 = this.treesPerChunk;
			
			if (random.nextFloat() < this.extraTreeChance) {
				
				++k1;
				
			}
			
			if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, this.chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE)) {
				
				for (int j2 = 0; j2 < k1; ++j2) {
					
					int k6 = random.nextInt(16) + 8;
					int l = random.nextInt(16) + 8;
					WorldGenAbstractTree worldgenabstracttree = biomeIn.getRandomTreeFeature(random);
					worldgenabstracttree.setDecorationDefaults();
					BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));
					
					if (worldgenabstracttree.generate(worldIn, random, blockpos)) {
						
						worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
						
					}
					
				}
				
			}
			
			/*
			 * if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random,
			 * chunkPos,
			 * net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.
			 * FLOWERS)) { for (int l2 = 0; l2 < this.flowersPerChunk; ++l2) { int i7 =
			 * random.nextInt(16) + 8; int l10 = random.nextInt(16) + 8; int j14 =
			 * worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32; if (j14 > 0) {
			 * int k17 = random.nextInt(j14); BlockPos blockpos1 = this.chunkPos.add(i7,
			 * k17, l10); BlockFlower.EnumFlowerType blockflower$enumflowertype =
			 * biomeIn.pickRandomFlower(random, blockpos1); BlockFlower blockflower =
			 * blockflower$enumflowertype.getBlockType().getBlock(); if
			 * (blockflower.getDefaultState().getMaterial() != Material.AIR) {
			 * this.flowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
			 * this.flowerGen.generate(worldIn, random, blockpos1); } } } }TODO: Flowers
			 */
			
			if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, this.chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS)) {
				
				for (int i3 = 0; i3 < this.grassPerChunk; ++i3) {
					
					int j7 = random.nextInt(16) + 8;
					int i11 = random.nextInt(16) + 8;
					int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;
					
					if (k14 > 0) {
						
						int l17 = random.nextInt(k14);
						biomeIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
						
					}
					
				}
				
			}
			
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(worldIn, random, this.chunkPos));
			
		}
		
		@Override
		protected void generateOres(World worldIn, Random random) {
			
			net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, random, this.chunkPos));
			
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, this.foligenuGen, this.chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
				
				this.genStandardOre1(worldIn, random, 14, this.foligenuGen, 0, 255);
				this.genStandardOre1(worldIn, random, 8, this.floatstoneShardDirtGen, 0, 255);
				this.genStandardOre1(worldIn, random, 8, this.floatstoneShardGen, 0, 255);
				
			}
			
			net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, random, this.chunkPos));
			
		}
		
	}
	
}
