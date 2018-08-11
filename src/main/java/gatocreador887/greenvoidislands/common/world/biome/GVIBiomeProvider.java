package gatocreador887.greenvoidislands.common.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gatocreador887.greenvoidislands.common.world.genlayer.GenLayerFix;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GVIBiomeProvider extends BiomeProvider {
	
	private GenLayer genBiomes;
	/** A GenLayer containing the indices into Biome.biomeList[] */
	private GenLayer biomeIndexLayer;
	/** The BiomeCache object for this world. */
	private BiomeCache biomeCache;
	/** A list of biomes that the player can spawn in. */
	private List<Biome> biomesToSpawnIn;
	
	public GVIBiomeProvider() {
		
		this.biomeCache = new BiomeCache(this);
		this.biomesToSpawnIn = new ArrayList();
		this.biomesToSpawnIn.addAll(allowedBiomes);
		
	}
	
	public GVIBiomeProvider(long seed, WorldType worldType) {
		
		this();
		GenLayer[] agenlayer = GenLayerFix.makeTheWorld(seed, worldType);
		agenlayer = this.getModdedBiomeGenerators(worldType, seed, agenlayer);
		this.genBiomes = agenlayer[0];
		this.biomeIndexLayer = agenlayer[1];
		
	}
	
	public GVIBiomeProvider(World world) {
		
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
		
	}
	
	/**
	 * Gets the list of valid biomes for the player to spawn in.
	 */
	@Override
	public List<Biome> getBiomesToSpawnIn() {
		
		return this.biomesToSpawnIn;
		
	}
	
	/**
	 * Returns the Biome related to the x, z position on the world.
	 */
	public Biome getBiomeGenerator(BlockPos pos) {
		
		return this.getBiomeGenerator(pos, (Biome) null);
		
	}
	
	public Biome getBiomeGenerator(BlockPos pos, Biome biomeGenBaseIn) {
		
		return this.biomeCache.getBiome(pos.getX(), pos.getZ(), biomeGenBaseIn);
		
	}
	
	/**
	 * Return an adjusted version of a given temperature based on the y height
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public float getTemperatureAtHeight(float par1, int par2) {
		
		return par1;
		
	}
	
	/**
	 * Returns an array of biomes for the location input.
	 */
	@Override
	public Biome[] getBiomesForGeneration(Biome[] par1ArrayOfBiome, int par2, int par3, int par4, int par5) {
		
		IntCache.resetIntCache();
		
		if (par1ArrayOfBiome == null || par1ArrayOfBiome.length < par4 * par5) {
			
			par1ArrayOfBiome = new Biome[par4 * par5];
			
		}
		
		int[] aint = this.genBiomes.getInts(par2, par3, par4, par5);
		
		try {
			
			for (int i = 0; i < par4 * par5; ++i) {
				
				par1ArrayOfBiome[i] = Biome.getBiome(aint[i]);
				
			}
			
			return par1ArrayOfBiome;
			
		} catch (Throwable throwable) {
			
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
			crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(par1ArrayOfBiome.length));
			crashreportcategory.addCrashSection("x", Integer.valueOf(par2));
			crashreportcategory.addCrashSection("z", Integer.valueOf(par3));
			crashreportcategory.addCrashSection("w", Integer.valueOf(par4));
			crashreportcategory.addCrashSection("h", Integer.valueOf(par5));
			throw new ReportedException(crashreport);
			
		}
	}
	
	/**
	 * Returns biomes to use for the blocks and loads the other data like
	 * temperature and humidity onto the WorldChunkManager Args: oldBiomeList, x, z,
	 * width, depth
	 */
	@Override
	public Biome[] getBiomes(Biome[] oldBiomeList, int x, int z, int width, int depth) {
		
		return this.getBiomes(oldBiomeList, x, z, width, depth, true);
		
	}
	
	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x, y,
	 * width, length, cacheFlag (if false, don't check biomeCache to avoid infinite
	 * loop in BiomeCacheBlock)
	 */
	@Override
	public Biome[] getBiomes(Biome[] listToReuse, int x, int y, int width, int length, boolean cacheFlag) {
		
		IntCache.resetIntCache();
		
		if (listToReuse == null || listToReuse.length < width * length) {
			
			listToReuse = new Biome[width * length];
			
		}
		
		if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (y & 15) == 0) {
			
			Biome[] aBiome1 = this.biomeCache.getCachedBiomes(x, y);
			System.arraycopy(aBiome1, 0, listToReuse, 0, width * length);
			return listToReuse;
			
		} else {
			
			int[] aint = this.biomeIndexLayer.getInts(x, y, width, length);
			
			for (int i = 0; i < width * length; ++i) {
				
				listToReuse[i] = Biome.getBiome(aint[i]);
				
			}
			
			return listToReuse;
			
		}
	}
	
	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	@Override
	public boolean areBiomesViable(int x, int y, int z, List par4List) {
		
		IntCache.resetIntCache();
		int l = x - z >> 2;
		int i1 = y - z >> 2;
		int j1 = x + z >> 2;
		int k1 = y + z >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
		
		try {
			
			for (int j2 = 0; j2 < l1 * i2; ++j2) {
				
				Biome biome = Biome.getBiome(aint[j2]);
				
				if (!par4List.contains(biome)) {
					
					return false;
					
				}
				
			}
			
			return true;
			
		} catch (Throwable throwable) {
			
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
			crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
			crashreportcategory.addCrashSection("x", Integer.valueOf(x));
			crashreportcategory.addCrashSection("z", Integer.valueOf(y));
			crashreportcategory.addCrashSection("radius", Integer.valueOf(z));
			crashreportcategory.addCrashSection("allowed", par4List);
			throw new ReportedException(crashreport);
			
		}
		
	}
	
	/**
	 * Finds a valid position within a range, that is in one of the listed biomes.
	 * Searches {par1,par2} +-par3 blocks. Strongly favors positive y positions.
	 */
	@Override
	public BlockPos findBiomePosition(int x, int z, int range, List biomes, Random random) {
		
		IntCache.resetIntCache();
		int l = x - range >> 2;
		int i1 = z - range >> 2;
		int j1 = x + range >> 2;
		int k1 = z + range >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
		BlockPos blockpos = null;
		int j2 = 0;
		
		for (int k2 = 0; k2 < l1 * i2; ++k2) {
			
			int l2 = l + k2 % l1 << 2;
			int i3 = i1 + k2 / l1 << 2;
			Biome biome = Biome.getBiome(aint[k2]);
			
			if (biomes.contains(biome) && (blockpos == null || random.nextInt(j2 + 1) == 0)) {
				
				blockpos = new BlockPos(l2, 0, i3);
				++j2;
				
			}
		}
		
		return blockpos;
		
	}
	
	/**
	 * Calls the WorldChunkManager's biomeCache.cleanupCache()
	 */
	@Override
	public void cleanupCache() {
		
		this.biomeCache.cleanupCache();
		
	}
	
}