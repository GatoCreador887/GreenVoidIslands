package creepersgalore.greenvoidislands.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import creepersgalore.greenvoidislands.client.core.GVICreativeTabManager;
import creepersgalore.greenvoidislands.common.core.GVIBlockManager;

public class BlockDeadIslandGrass extends Block implements IGrowable
{

    public BlockDeadIslandGrass()
    {
        super(Material.GRASS);
        this.setTickRandomly(true);
        this.setCreativeTab(GVICreativeTabManager.materials);
        this.setHardness(0.6F);
        this.setSoundType(SoundType.PLANT);
        this.setHarvestLevel("shovel", 0);
        this.setRegistryName("dead_island_grass");
        ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable) {
    	
    	return true;
    	
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		
		if (rand.nextInt(3) == 0 && worldIn.getLightFromNeighbors(pos.up()) >= 2 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) <= 2 && worldIn.getLightFromNeighbors(pos.up()) < 13) {
			
			worldIn.setBlockState(pos, GVIBlockManager.island_grass.getDefaultState());
			
		}
		
	}
	
}