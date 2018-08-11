package gatocreador887.greenvoidislands.common.world.gen;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import gatocreador887.greenvoidislands.common.world.gen.feature.WorldGenFoligenuStructure1;
import gatocreador887.greenvoidislands.common.world.gen.feature.WorldGenFoligenuStructure2;
import gatocreador887.greenvoidislands.common.world.gen.feature.WorldGenGrassIsland;
import gatocreador887.greenvoidislands.common.world.gen.feature.WorldGenGreenVoidIsland;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

public class GVIChunkProvider implements IChunkGenerator {
	/** RNG. */
	private Random rand;
	protected static final IBlockState END_STONE = Blocks.DIRT.getDefaultState();
	protected static final IBlockState END_STONE2 = Blocks.DIRT.getDefaultState();
	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private NoiseGeneratorOctaves lperlin1;
	private NoiseGeneratorOctaves lperlin2;
	private NoiseGeneratorOctaves perlin;
	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen5;
	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen6;
	/** Reference to the World object. */
	private final World world;
	private NoiseGeneratorSimplex islandNoise;
	private double[] field_185974_p;
	/** The biomes that are used to generate the chunk */
	private Biome[] biomesForGeneration;
	double[] field_185966_e;
	double[] field_185967_f;
	double[] field_185968_g;
	private final WorldGenGreenVoidIsland field_185975_r = new WorldGenGreenVoidIsland();
	private final WorldGenGrassIsland grassisland = new WorldGenGrassIsland();
	private final WorldGenFoligenuStructure1 foligenu1 = new WorldGenFoligenuStructure1();
	private final WorldGenFoligenuStructure2 foligenu2 = new WorldGenFoligenuStructure2();
	// temporary variables used during event handling
	private int chunkX = 0;
	private int chunkZ = 0;
	
	public GVIChunkProvider(World worldObjIn, long seed) {
		this.world = worldObjIn;
		this.rand = new Random(seed);
		this.lperlin1 = new NoiseGeneratorOctaves(this.rand, 14);
		this.lperlin2 = new NoiseGeneratorOctaves(this.rand, 12);
		this.perlin = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.islandNoise = new NoiseGeneratorSimplex(this.rand);
		
		net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd ctx = new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd(this.lperlin1, this.lperlin2, this.perlin, this.noiseGen5, this.noiseGen6, this.islandNoise);
		ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(worldObjIn, this.rand, ctx);
		this.lperlin1 = ctx.getLPerlin1();
		this.lperlin2 = ctx.getLPerlin2();
		this.perlin = ctx.getPerlin();
		this.noiseGen5 = ctx.getDepth();
		this.noiseGen6 = ctx.getScale();
		this.islandNoise = ctx.getIsland();
	}
	
	/**
	 * Generates a bare-bones chunk of nothing but stone or ocean blocks, formed,
	 * but featureless.
	 */
	public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
		int i = 2;
		int j = i + 1;
		int k = 33;
		int l = i + 1;
		this.field_185974_p = this.genNoiseArray(this.field_185974_p, x * i, 0, z * i, j, k, l);
		
		for (int i1 = 0; i1 < i; ++i1) {
			for (int j1 = 0; j1 < i; ++j1) {
				for (int k1 = 0; k1 < 32; ++k1) {
					double d0 = 0.25D;
					double d1 = this.field_185974_p[((i1 + 0) * l + j1 + 0) * k + k1 + 0];
					double d2 = this.field_185974_p[((i1 + 0) * l + j1 + 1) * k + k1 + 0];
					double d3 = this.field_185974_p[((i1 + 1) * l + j1 + 0) * k + k1 + 0];
					double d4 = this.field_185974_p[((i1 + 1) * l + j1 + 1) * k + k1 + 0];
					double d5 = (this.field_185974_p[((i1 + 0) * l + j1 + 0) * k + k1 + 1] - d1) * d0;
					double d6 = (this.field_185974_p[((i1 + 0) * l + j1 + 1) * k + k1 + 1] - d2) * d0;
					double d7 = (this.field_185974_p[((i1 + 1) * l + j1 + 0) * k + k1 + 1] - d3) * d0;
					double d8 = (this.field_185974_p[((i1 + 1) * l + j1 + 1) * k + k1 + 1] - d4) * d0;
					
					for (int l1 = 0; l1 < 4; ++l1) {
						double d9 = 0.125D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						
						for (int i2 = 0; i2 < 8; ++i2) {
							double d14 = 0.125D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							
							for (int j2 = 0; j2 < 8; ++j2) {
								IBlockState iblockstate = AIR;
								
								if (d15 > 0.0D) {
									
									int l3 = l1 + k1 * 4;
									
									if (l3 < 36 + this.rand.nextInt(5)) {
										
										iblockstate = Blocks.STONE.getDefaultState();
										
									} else {
										
										iblockstate = END_STONE;
										
									}
									
								}
								
								int k2 = i2 + i1 * 8;
								int l2 = l1 + k1 * 4;
								int i3 = j2 + j1 * 8;
								
								primer.setBlockState(k2, l2, i3, iblockstate);
								
								d15 += d16;
							}
							
							d10 += d12;
							d11 += d13;
						}
						
						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
		
	}
	
	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
		if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, this.chunkX, this.chunkZ, primer, this.world)) {
			return;
			/*
			 * for (int i = 0; i < 16; ++i) { for (int j = 0; j < 16; ++j) { int k = 1; int
			 * l = -1; IBlockState iblockstate = END_STONE; IBlockState iblockstate1 =
			 * END_STONE2; for (int i1 = 127; i1 >= 0; --i1) { IBlockState iblockstate2 =
			 * primer.getBlockState(i, i1, j); if (iblockstate2.getMaterial() ==
			 * Material.AIR) { l = -1; } else if (iblockstate2.getBlock() == Blocks.STONE) {
			 * if (l == -1) { if (k <= 0) { iblockstate = AIR; iblockstate1 = END_STONE; } l
			 * = k; if (i1 >= 0) { primer.setBlockState(i, i1, j, iblockstate); } else {
			 * primer.setBlockState(i, i1, j, iblockstate1); } } else if (l > 0) { --l;
			 * primer.setBlockState(i, i1, j, iblockstate1); } } } What's this for??? ^ for
			 * (int y = 0; y < 36 + rand.nextInt(5); y++) { if (worldObj.getBlockState(new
			 * BlockPos(xo, y, zo)).getBlock() != Blocks.AIR) { worldObj.setBlockState(new
			 * BlockPos(xo, y, zo), Blocks.GLASS.getDefaultState()); } } } }
			 */
		}
		
		for (int i = 0; i < 16; i++) {
			
			for (int j = 0; j < 16; j++) {
				
				// Replace top blocks
				
				int i1 = x * 16 + i & 15;
				int j1 = z * 16 + j & 15;
				
				Biome biome = biomesIn[j + i * 16];
				
				int groundY = this.findGroundY(primer, j1, i1);
				
				if (groundY > 0) {
					
					primer.setBlockState(j1, groundY, i1, biome.topBlock);
					
				}
				
			}
			
		}
		
	}
	
	public int findGroundY(ChunkPrimer primer, int x, int z) {
		for (int y = 255; y > 0; y--) {
			if (primer.getBlockState(x, y, z).getBlock() != Blocks.AIR) {
				return y;
			}
		}
		return 0;
	}
	
	@Override
	public Chunk generateChunk(int x, int z) {
		
		this.chunkX = x;
		this.chunkZ = z;
		this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		this.setBlocksInChunk(x, z, chunkprimer);
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
		
		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
		byte[] abyte = chunk.getBiomeArray();
		
		for (int i = 0; i < abyte.length; ++i) {
			
			abyte[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
			
		}
		
		chunk.generateSkylightMap();
		return chunk;
		
	}
	
	private float calcIslandRandomness(int x, int z, int offsetX, int offsetZ) {
		float f = (float) (x * 2 + offsetX);
		float f1 = (float) (z * 2 + offsetZ);
		float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;
		
		if (f2 > 80.0F) {
			f2 = 80.0F;
		}
		
		if (f2 < -100.0F) {
			f2 = -100.0F;
		}
		
		for (int i = -12; i <= 12; ++i) {
			for (int j = -12; j <= 12; ++j) {
				long k = (long) (x + i);
				long l = (long) (z + j);
				
				if (k * k + l * l > 4096L && this.islandNoise.getValue((double) k, (double) l) < -0.8999999761581421D) {
					float f3 = (MathHelper.abs((float) k) * 3439.0F + MathHelper.abs((float) l) * 147.0F) % 13.0F + 9.0F;
					f = (float) (offsetX - i * 2);
					f1 = (float) (offsetZ - j * 2);
					float f4 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * f3;
					
					if (f4 > 80.0F) {
						f4 = 80.0F;
					}
					
					if (f4 < -100.0F) {
						f4 = -100.0F;
					}
					
					if (f4 > f2) {
						f2 = f4;
					}
				}
			}
		}
		
		return f2;
	}
	
	/*public boolean func_185961_c(int p_185961_1_, int p_185961_2_) {
		return (long) p_185961_1_ * (long) p_185961_1_ + (long) p_185961_2_ * (long) p_185961_2_ > 4096L && this.calcIslandRandomness(p_185961_1_, p_185961_2_, 1, 1) >= 0.0F;
	}*/
	
	private double[] genNoiseArray(double[] noiseArray, int x, int y, int z, int scaleX, int scaleY, int scaleZ) {
		net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField event = new net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField(this, noiseArray, x, y, z, scaleX, scaleY, scaleZ);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) {
			return event.getNoisefield();
		}
		
		if (noiseArray == null) {
			noiseArray = new double[scaleX * scaleY * scaleZ];
		}
		
		double d0 = 684.412D;
		double d1 = 684.412D;
		d0 = d0 * 2.0D;
		this.field_185966_e = this.perlin.generateNoiseOctaves(this.field_185966_e, x, y, z, scaleX, scaleY, scaleZ, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
		this.field_185967_f = this.lperlin1.generateNoiseOctaves(this.field_185967_f, x, y, z, scaleX, scaleY, scaleZ, d0, d1, d0);
		this.field_185968_g = this.lperlin2.generateNoiseOctaves(this.field_185968_g, x, y, z, scaleX, scaleY, scaleZ, d0, d1, d0);
		int i = x / 2;
		int j = z / 2;
		int k = 0;
		
		for (int l = 0; l < scaleX; ++l) {
			for (int i1 = 0; i1 < scaleZ; ++i1) {
				float f = this.calcIslandRandomness(i, j, l, i1);
				
				for (int j1 = 0; j1 < scaleY; ++j1) {
					double d2 = 0.0D;
					double d3 = this.field_185967_f[k] / 512.0D;
					double d4 = this.field_185968_g[k] / 512.0D;
					double d5 = (this.field_185966_e[k] / 10.0D + 1.0D) / 2.0D;
					
					if (d5 < 0.0D) {
						d2 = d3;
					} else if (d5 > 1.0D) {
						d2 = d4;
					} else {
						d2 = d3 + (d4 - d3) * d5;
					}
					
					d2 = d2 - 8.0D;
					d2 = d2 + (double) f;
					int k1 = 2;
					
					if (j1 > scaleY / 2 - k1) {
						double d6 = (double) ((float) (j1 - (scaleY / 2 - k1)) / 64.0F);
						d6 = MathHelper.clamp(d6, 0.0D, 1.0D);
						d2 = d2 * (1.0D - d6) + -3000.0D * d6;
					}
					
					k1 = 8;
					
					if (j1 < k1) {
						double d7 = (double) ((float) (k1 - j1) / ((float) k1 - 1.0F));
						d2 = d2 * (1.0D - d7) + -30.0D * d7;
					}
					
					noiseArray[k] = d2;
					++k;
				}
			}
		}
		
		return noiseArray;
	}
	
	@Override
	public void populate(int x, int z) {
		
		BlockFalling.fallInstantly = true;
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
		BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
		
		/*
		 * for (int xo = blockpos.getX(); xo < blockpos.getX() + 16; xo++) { for (int zo
		 * = blockpos.getZ(); zo < blockpos.getZ() + 16; zo++) { int groundY =
		 * findGroundY(xo, zo); if (groundY > 0) { worldObj.setBlockState(new
		 * BlockPos(xo, groundY, zo), this.worldObj.getBiome(new BlockPos(xo, groundY,
		 * zo)).topBlock); } for (int y = 0; y < 36 + rand.nextInt(5); y++) { if
		 * (worldObj.getBlockState(new BlockPos(xo, y, zo)).getBlock() != Blocks.AIR) {
		 * worldObj.setBlockState(new BlockPos(xo, y, zo),
		 * Blocks.GLASS.getDefaultState()); } } } }
		 */
		
		if (/* i > 4096L */true) {
			float f = this.calcIslandRandomness(x, z, 1, 1);
			
			if (/* f < -20.0F && */this.rand.nextInt(42) == 0) {
				this.grassisland.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 35 + this.rand.nextInt(46), this.rand.nextInt(16) + 8));
				
				if (this.rand.nextInt(8) == 0) {
					this.grassisland.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 35 + this.rand.nextInt(46), this.rand.nextInt(16) + 8));
				}
				
			}
			if (f < -20.0F && this.rand.nextInt(24) == 0) {
				
				this.foligenu1.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(6) + 8, 8 + this.rand.nextInt(88), this.rand.nextInt(6) + 8));
				
				if (this.rand.nextInt(2) == 0) {
					
					this.foligenu2.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(6) + 8, 8 + this.rand.nextInt(88), this.rand.nextInt(6) + 8));
					
				}
				
			}
			
		}
		
		this.world.getBiome(blockpos.add(16, 0, 16)).decorate(this.world, this.world.rand, blockpos);
		long i = (long) x * (long) x + (long) z * (long) z;
		
		int var4 = x * 16;
		int var5 = z * 16;
		
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
		BlockFalling.fallInstantly = false;
	}
	
	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		
		return false;
	}
	
	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return this.world.getBiome(pos).getSpawnableList(creatureType);
	}
	
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean bool) {
		return null;
	}
	
	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {}
	
	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		
		return false;
		
	}
	
	@Override
	@Nullable
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		
		return null;
		
	}
	
}