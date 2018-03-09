package creepersgalore.greenvoidislands.common.world.gen.feature;

import java.util.Random;

import creepersgalore.greenvoidislands.common.block.BlockDoubleTallIslandGrass;
import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDoubleTallIslandGrass extends WorldGenerator
{
    private BlockDoubleTallIslandGrass.EnumPlantType plantType;

    public void setPlantType(BlockDoubleTallIslandGrass.EnumPlantType plantTypeIn)
    {
        this.plantType = plantTypeIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        boolean flag = false;

        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 254) && GVIBlockManager.double_tall_island_grass.canPlaceBlockAt(worldIn, blockpos))
            {
                ((BlockDoubleTallIslandGrass) GVIBlockManager.double_tall_island_grass).placeAt(worldIn, blockpos, this.plantType, 2);
                flag = true;
            }
        }

        return flag;
    }
}