package creepersgalore.greenvoidislands.common.world.gen.feature;

import java.util.Random;

import creepersgalore.greenvoidislands.common.block.BlockTallIslandGrass;
import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTallIslandGrass extends WorldGenerator
{
    private final IBlockState tallGrassState;

    public WorldGenTallIslandGrass(BlockTallIslandGrass.EnumType p_i45629_1_)
    {
        this.tallGrassState = GVIBlockManager.tall_island_grass.getDefaultState().withProperty(BlockTallIslandGrass.TYPE, p_i45629_1_);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
        {
            position = position.down();
        }

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && ((BlockTallIslandGrass) GVIBlockManager.tall_island_grass).canBlockStay(worldIn, blockpos, this.tallGrassState))
            {
                worldIn.setBlockState(blockpos, this.tallGrassState, 2);
            }
        }

        return true;
    }
}