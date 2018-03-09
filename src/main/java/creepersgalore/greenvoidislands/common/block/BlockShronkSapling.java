package creepersgalore.greenvoidislands.common.block;

import java.util.Random;

import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockShronkSapling extends BlockBush implements IGrowable
{
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 3);
    protected static final AxisAlignedBB PIT_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.075000011920929D, 0.8999999761581421D);
    protected static final AxisAlignedBB SPROUT_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.250000011920929D, 0.8999999761581421D);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockShronkSapling()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
        //this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setRegistryName("shronk_sapling");
        this.setSoundType(SoundType.PLANT);
		ForgeRegistries.ITEMS.register(new ItemBlock(this)
		{
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
		    {
		        IBlockState iblockstate = worldIn.getBlockState(pos);
		        Block block = iblockstate.getBlock();

		        if (!block.isReplaceable(worldIn, pos))
		        {
		            pos = pos.offset(facing);
		        }

		        ItemStack itemstack = player.getHeldItem(hand);

		        if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, (Entity)null))
		        {
		            int i = this.getMetadata(itemstack.getMetadata());
		            IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

		            if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
		            {
		                iblockstate1 = worldIn.getBlockState(pos);
		                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
		                worldIn.playSound(player, pos, SoundType.GROUND.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.65F);
		                itemstack.shrink(1);
		            }

		            return EnumActionResult.SUCCESS;
		        }
		        else
		        {
		            return EnumActionResult.FAIL;
		        }
		    }
		}
		.setRegistryName("shronk_pit"));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if (state.getValue(STAGE) < 1)
        {
        	return PIT_AABB;
        }
        else if (state.getValue(STAGE) < 2)
        {
        	return SPROUT_AABB;
        }
        else
        {
        	return SAPLING_AABB;
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);

            if (worldIn.getLightFromNeighbors(pos.up()) >= 5 && worldIn.getLightFromNeighbors(pos.up()) <= 14 && rand.nextInt(7) == 0)
            {
                this.grow(worldIn, pos, state, rand, false);
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand, boolean bonemeal)
    {
        if (((Integer)state.getValue(STAGE)).intValue() < 3)
        {
        	if (((Integer)state.getValue(STAGE)).intValue() == 0)
        	{
        		if (rand.nextInt(10) == 0 || bonemeal)
        		{
        			worldIn.setBlockState(pos, state.cycleProperty(STAGE));
        		}
        		else
        		{
        			worldIn.setBlockToAir(pos);
        		}
        	}
        	else if (((Integer)state.getValue(STAGE)).intValue() == 1)
        	{
        		if (rand.nextInt(5) == 0 || bonemeal)
        		{
        			worldIn.setBlockState(pos, state.cycleProperty(STAGE));
        		}
        		else
        		{
        			worldIn.setBlockToAir(pos);
        		}
        	}
        	else
        	{
        		worldIn.setBlockState(pos, state.cycleProperty(STAGE));
        	}
        }
        else
        {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldgenerator = new WorldGenTrees(false, 7, GVIBlockManager.shronk_log.getDefaultState(), GVIBlockManager.shronk_leaves.getDefaultState(), false);
        int i = 0;
        int j = 0;
        boolean flag = false;

        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

        if (flag)
        {
            worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
        }
        else
        {
            worldIn.setBlockState(pos, iblockstate2, 4);
        }

        if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j)))
        {
            if (flag)
            {
                worldIn.setBlockState(pos.add(i, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            }
            else
            {
                worldIn.setBlockState(pos, state, 4);
            }
        }
    }

    /**
     * Whether this IGrowable can grow
     */
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.grow(worldIn, pos, state, rand, true);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(STAGE, Integer.valueOf((meta)));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(STAGE)).intValue();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {STAGE});
    }
}