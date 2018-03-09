package creepersgalore.greenvoidislands.common.world.gen.feature;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import creepersgalore.greenvoidislands.common.core.GVIBlockManager;

public class WorldGenFoligenuStructure2 extends WorldGenerator
{
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        if (!worldIn.isAirBlock(position))
        {
            return false;
        }
        else if (worldIn.getBlockState(position.up()).getBlock() != Blocks.DIRT)
        {
            return false;
        }
        else
        {
            worldIn.setBlockState(position, GVIBlockManager.foligenu_block.getDefaultState(), 2);

            for (int i = 0; i < 1500; ++i)
            {
                BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));

                if (worldIn.isAirBlock(blockpos))
                {
                    int j = 0;

                    for (EnumFacing enumfacing : EnumFacing.values())
                    {
                        if (worldIn.getBlockState(blockpos.offset(enumfacing)).getBlock() == GVIBlockManager.foligenu_block)
                        {
                            ++j;
                        }

                        if (j > 1)
                        {
                            break;
                        }
                    }

                    if (j == 1)
                    {
                        worldIn.setBlockState(blockpos, GVIBlockManager.foligenu_block.getDefaultState(), 2);
                    }
                }
            }

            return true;
        }
    }
}