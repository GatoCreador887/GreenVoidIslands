package gatocreador887.greenvoidislands.common.world.gen.feature;

import java.util.Random;

import gatocreador887.greenvoidislands.common.block.BlockDoubleTallIslandGrass;
import gatocreador887.greenvoidislands.common.core.GVIBlockManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDoubleTallIslandGrass extends WorldGenerator {
	private BlockDoubleTallIslandGrass.EnumPlantType plantType;
	
	public void setPlantType(BlockDoubleTallIslandGrass.EnumPlantType plantTypeIn) {
		this.plantType = plantTypeIn;
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		boolean flag = false;
		
		for (int i = 0; i < 64; ++i) {
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
			
			if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 254) && GVIBlockManager.DOUBLE_TALL_ISLAND_GRASS.canPlaceBlockAt(worldIn, blockpos)) {
				((BlockDoubleTallIslandGrass) GVIBlockManager.DOUBLE_TALL_ISLAND_GRASS).placeAt(worldIn, blockpos, this.plantType, 2);
				flag = true;
			}
		}
		
		return flag;
	}
}