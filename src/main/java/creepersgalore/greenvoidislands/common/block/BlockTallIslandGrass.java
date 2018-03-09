package creepersgalore.greenvoidislands.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockTallIslandGrass extends BlockBush implements IGrowable, net.minecraftforge.common.IShearable
{
    public static final PropertyEnum<BlockTallIslandGrass.EnumType> TYPE = PropertyEnum.<BlockTallIslandGrass.EnumType>create("type", BlockTallIslandGrass.EnumType.class);
    protected static final AxisAlignedBB TALL_GRASS_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockTallIslandGrass()
    {
        super(Material.VINE);
        this.setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockTallIslandGrass.EnumType.GRASS));
        this.setRegistryName("tall_island_grass");
        ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return TALL_GRASS_AABB;
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        return super.canBlockStay(worldIn, pos, state);
    }
    
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (worldIn.getLightFromNeighbors(pos.up()) < 2 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2)
            {
                if (this.getMetaFromState(this.getBlockState().getBaseState()) == EnumType.GRASS.meta)
                {
                	worldIn.setBlockState(pos, this.getDefaultState().withProperty(TYPE, EnumType.DEAD_GRASS));
                }
                else if (this.getMetaFromState(this.getBlockState().getBaseState()) == EnumType.FERN.meta)
                {
                	worldIn.setBlockState(pos, this.getDefaultState().withProperty(TYPE, EnumType.DEAD_FERN));
                }
            }
            else
            {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 4)
                {
                	
                	if (worldIn.getLightFromNeighbors(pos.up()) >= 13)
                    {
                    	
                		if (this.getMetaFromState(this.getBlockState().getBaseState()) == EnumType.GRASS.meta)
                        {
                        	worldIn.setBlockState(pos, this.getDefaultState().withProperty(TYPE, EnumType.DEAD_GRASS));
                        }
                        else if (this.getMetaFromState(this.getBlockState().getBaseState()) == EnumType.FERN.meta)
                        {
                        	worldIn.setBlockState(pos, this.getDefaultState().withProperty(TYPE, EnumType.DEAD_FERN));
                        }
                		
                    }
                	
                    /*for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == Blocks.DIRT && iblockstate1.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && worldIn.getLightFromNeighbors(blockpos.up()) < 13 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
                        {
                            worldIn.setBlockState(blockpos, GVIBlockManager.island_grass.getDefaultState());
                        }
                    }*/
                }
            }
        }
    }

    /**
     * Whether this Block can be replaced directly by other blocks (true for e.g. tall grass)
     */
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return 1 + random.nextInt(fortune * 2 + 1);
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(StatList.getBlockStats(this));
            spawnAsEntity(worldIn, pos, new ItemStack(GVIBlockManager.tall_island_grass, 1, ((BlockTallIslandGrass.EnumType)state.getValue(TYPE)).getMeta()));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, state.getBlock().getMetaFromState(state));
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (int i = 1; i < 3; ++i)
        {
            items.add(new ItemStack(this, 1, i));
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
        return true;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        BlockDoubleTallIslandGrass.EnumPlantType blockdoubleplant$enumplanttype = BlockDoubleTallIslandGrass.EnumPlantType.GRASS;

        if (state.getValue(TYPE) == BlockTallIslandGrass.EnumType.FERN)
        {
            blockdoubleplant$enumplanttype = BlockDoubleTallIslandGrass.EnumPlantType.FERN;
        }

        if (GVIBlockManager.double_tall_island_grass.canPlaceBlockAt(worldIn, pos))
        {
            ((BlockDoubleTallIslandGrass) GVIBlockManager.double_tall_island_grass).placeAt(worldIn, pos, blockdoubleplant$enumplanttype, 2);
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE, BlockTallIslandGrass.EnumType.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockTallIslandGrass.EnumType)state.getValue(TYPE)).getMeta();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XYZ;
    }

    public static enum EnumType implements IStringSerializable
    {
        GRASS(0, "tall_grass"),
        FERN(1, "fern"),
        DEAD_GRASS(2, "dead_tall_grass"),
        DEAD_FERN(3, "dead_fern");

        private static final BlockTallIslandGrass.EnumType[] META_LOOKUP = new BlockTallIslandGrass.EnumType[values().length];
        private final int meta;
        private final String name;

        private EnumType(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta()
        {
            return this.meta;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockTallIslandGrass.EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        public String getName()
        {
            return this.name;
        }

        static
        {
            for (BlockTallIslandGrass.EnumType blocktallgrass$enumtype : values())
            {
                META_LOOKUP[blocktallgrass$enumtype.getMeta()] = blocktallgrass$enumtype;
            }
        }
    }

    @Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos){ return true; }
    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return NonNullList.withSize(1, new ItemStack(Blocks.TALLGRASS, 1, ((BlockTallIslandGrass.EnumType)world.getBlockState(pos).getValue(TYPE)).getMeta()));
    }
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        //if (RANDOM.nextInt(8) != 0) return;
        //ItemStack seed = net.minecraftforge.common.ForgeHooks.getGrassSeed(RANDOM, fortune);
        //if (!seed.isEmpty())
            //drops.add(seed);
    }
}