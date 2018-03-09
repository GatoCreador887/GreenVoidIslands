package creepersgalore.greenvoidislands.common.world.gen;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import creepersgalore.greenvoidislands.common.core.GVIBlockManager;

public class GVIWorldGen implements IWorldGenerator {
	
	private WorldGenerator gen_foligenu_ore;
	private WorldGenerator gen_gvi_foligenu_ore;
	
	public GVIWorldGen() {
		
		this.gen_foligenu_ore = new WorldGenMinable(GVIBlockManager.foligenu_ore.getDefaultState(), 6);
		this.gen_gvi_foligenu_ore = new WorldGenMinable(GVIBlockManager.foligenu_ore.getDefaultState(), 8, BlockMatcher.forBlock(Blocks.GLASS));
		
	}
	
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		switch (world.provider.getDimension()) {
	    case 0: //Overworld
	    	
	    	this.runGenerator(gen_foligenu_ore, world, random, chunkX, chunkZ, 4 + random.nextInt(2), 11, 53 + random.nextInt(3));
	    	
	        break;
	        
	    case -1: //Nether
	    	
	        break;
	        
	    case 1: //End

	        break;
	    
	    case 7: //Green Void Islands
	    	
	    	//this.runGenerator(gen_gvi_foligenu_ore, world, random, chunkX, chunkZ, 5 + random.nextInt(3), 0, 255);
	    	
	    	break;
	    	
	    }
		
    }
    
    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chancesToSpawn; i ++) {
            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunk_Z * 16 + rand.nextInt(16);
            generator.generate(world, rand, new BlockPos(x, y, z));
        }
    }
	
}
