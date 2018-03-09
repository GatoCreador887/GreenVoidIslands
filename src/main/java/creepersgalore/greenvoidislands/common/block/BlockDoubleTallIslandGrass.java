package creepersgalore.greenvoidislands.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockDoubleTallIslandGrass extends BlockBush implements IGrowable, net.minecraftforge.common.IShearable
{
    public static final PropertyEnum<BlockDoubleTallIslandGrass.EnumPlantType> VARIANT = PropertyEnum.<BlockDoubleTallIslandGrass.EnumPlantType>create("variant", BlockDoubleTallIslandGrass.EnumPlantType.class);
    public static final PropertyEnum<BlockDoubleTallIslandGrass.EnumBlockHalf> HALF = PropertyEnum.<BlockDoubleTallIslandGrass.EnumBlockHalf>create("half", BlockDoubleTallIslandGrass.EnumBlockHalf.class);
    public static final PropertyEnum<EnumFacing> FACING = BlockHorizontal.FACING;

    public BlockDoubleTallIslandGrass()
    {
        super(Material.VINE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockDoubleTallIslandGrass.EnumPlantType.GRASS).withProperty(HALF, BlockDoubleTallIslandGrass.EnumBlockHalf.LOWER).withProperty(FACING, EnumFacing.NORTH));
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setRegistryName("double_tall_island_grass");
        ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FULL_BLOCK_AABB;
    }

    private BlockDoubleTallIslandGrass.EnumPlantType getType(IBlockAccess blockAccess, BlockPos pos, IBlockState state)
    {
        if (state.getBlock() == this)
        {
            state = state.getActualState(blockAccess, pos);
            return (BlockDoubleTallIslandGrass.EnumPlantType)state.getValue(VARIANT);
        }
        else
        {
            return BlockDoubleTallIslandGrass.EnumPlantType.GRASS;
        }
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up());
    }

    /**
     * Whether this Block can be replaced directly by other blocks (true for e.g. tall grass)
     */
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() != this)
        {
            return true;
        }
        else
        {
            BlockDoubleTallIslandGrass.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoubleTallIslandGrass.EnumPlantType)iblockstate.getActualState(worldIn, pos).getValue(VARIANT);
            return blockdoubleplant$enumplanttype == BlockDoubleTallIslandGrass.EnumPlantType.FERN || blockdoubleplant$enumplanttype == BlockDoubleTallIslandGrass.EnumPlantType.GRASS;
        }
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            boolean flag = state.getValue(HALF) == BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER;
            BlockPos blockpos = flag ? pos : pos.up();
            BlockPos blockpos1 = flag ? pos.down() : pos;
            Block block = (Block)(flag ? this : worldIn.getBlockState(blockpos).getBlock());
            Block block1 = (Block)(flag ? worldIn.getBlockState(blockpos1).getBlock() : this);

            if (!flag) this.dropBlockAsItem(worldIn, pos, state, 0); //Forge move above the setting to air.

            if (block == this)
            {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
            }

            if (block1 == this)
            {
                worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
            }
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getBlock() != this) return super.canBlockStay(worldIn, pos, state); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
        if (state.getValue(HALF) == BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER)
        {
            return worldIn.getBlockState(pos.down()).getBlock() == this;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.up());
            return iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate);
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (state.getValue(HALF) == BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER)
        {
            return Items.AIR;
        }
        else
        {
            BlockDoubleTallIslandGrass.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoubleTallIslandGrass.EnumPlantType)state.getValue(VARIANT);

            if (blockdoubleplant$enumplanttype == BlockDoubleTallIslandGrass.EnumPlantType.FERN)
            {
                return Items.AIR;
            }
            else if (blockdoubleplant$enumplanttype == BlockDoubleTallIslandGrass.EnumPlantType.GRASS)
            {
                return /*rand.nextInt(8) == 0 ? Items.WHEAT_SEEDS : */Items.AIR;
            }
            else
            {
                return super.getItemDropped(state, rand, fortune);
            }
        }
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state)
    {
        return state.getValue(HALF) != BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER && state.getValue(VARIANT) != BlockDoubleTallIslandGrass.EnumPlantType.GRASS ? ((BlockDoubleTallIslandGrass.EnumPlantType)state.getValue(VARIANT)).getMeta() : 0;
    }

    public void placeAt(World worldIn, BlockPos lowerPos, BlockDoubleTallIslandGrass.EnumPlantType variant, int flags)
    {
        worldIn.setBlockState(lowerPos, this.getDefaultState().withProperty(HALF, BlockDoubleTallIslandGrass.EnumBlockHalf.LOWER).withProperty(VARIANT, variant), flags);
        worldIn.setBlockState(lowerPos.up(), this.getDefaultState().withProperty(HALF, BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER), flags);
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER), 2);
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (state.getValue(HALF) == BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER)
        {
            if (worldIn.getBlockState(pos.down()).getBlock() == this)
            {
                if (player.capabilities.isCreativeMode)
                {
                    worldIn.setBlockToAir(pos.down());
                }
                else
                {
                    IBlockState iblockstate = worldIn.getBlockState(pos.down());
                    BlockDoubleTallIslandGrass.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoubleTallIslandGrass.EnumPlantType)iblockstate.getValue(VARIANT);

                    if (blockdoubleplant$enumplanttype != BlockDoubleTallIslandGrass.EnumPlantType.FERN && blockdoubleplant$enumplanttype != BlockDoubleTallIslandGrass.EnumPlantType.GRASS)
                    {
                        worldIn.destroyBlock(pos.down(), true);
                    }
                    else if (worldIn.isRemote)
                    {
                        worldIn.setBlockToAir(pos.down());
                    }
                    else if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == Items.SHEARS)
                    {
                        this.onHarvest(worldIn, pos, iblockstate, player);
                        worldIn.setBlockToAir(pos.down());
                    }
                    else
                    {
                        worldIn.destroyBlock(pos.down(), true);
                    }
                }
            }
        }
        else if (worldIn.getBlockState(pos.up()).getBlock() == this)
        {
            worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    private boolean onHarvest(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        BlockDoubleTallIslandGrass.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoubleTallIslandGrass.EnumPlantType)state.getValue(VARIANT);

        if (blockdoubleplant$enumplanttype != BlockDoubleTallIslandGrass.EnumPlantType.FERN && blockdoubleplant$enumplanttype != BlockDoubleTallIslandGrass.EnumPlantType.GRASS)
        {
            return false;
        }
        else
        {
            player.addStat(StatList.getBlockStats(this));
            return true;
        }
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (BlockDoubleTallIslandGrass.EnumPlantType blockdoubleplant$enumplanttype : BlockDoubleTallIslandGrass.EnumPlantType.values())
        {
            items.add(new ItemStack(this, 1, blockdoubleplant$enumplanttype.getMeta()));
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, this.getType(worldIn, pos, state).getMeta());
    }

    /**
     * Whether this IGrowable can grow
     */
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        BlockDoubleTallIslandGrass.EnumPlantType blockdoubleplant$enumplanttype = this.getType(worldIn, pos, state);
        return blockdoubleplant$enumplanttype != BlockDoubleTallIslandGrass.EnumPlantType.GRASS && blockdoubleplant$enumplanttype != BlockDoubleTallIslandGrass.EnumPlantType.FERN;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        spawnAsEntity(worldIn, pos, new ItemStack(this, 1, this.getType(worldIn, pos, state).getMeta()));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER) : this.getDefaultState().withProperty(HALF, BlockDoubleTallIslandGrass.EnumBlockHalf.LOWER).withProperty(VARIANT, BlockDoubleTallIslandGrass.EnumPlantType.byMetadata(meta & 7));
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (state.getValue(HALF) == BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.down());

            if (iblockstate.getBlock() == this)
            {
                state = state.withProperty(VARIANT, iblockstate.getValue(VARIANT));
            }
        }

        return state;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(HALF) == BlockDoubleTallIslandGrass.EnumBlockHalf.UPPER ? 8 | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex() : ((BlockDoubleTallIslandGrass.EnumPlantType)state.getValue(VARIANT)).getMeta();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {HALF, VARIANT, FACING});
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        EnumPlantType type = (EnumPlantType)state.getValue(VARIANT);
        return state.getValue(HALF) == EnumBlockHalf.LOWER && (type == EnumPlantType.FERN || type == EnumPlantType.GRASS);
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        EnumPlantType type = (EnumPlantType)world.getBlockState(pos).getValue(VARIANT);
        if (type == EnumPlantType.FERN) ret.add(new ItemStack(GVIBlockManager.tall_island_grass, 2, BlockTallIslandGrass.EnumType.FERN.getMeta()));
        if (type == EnumPlantType.GRASS) ret.add(new ItemStack(GVIBlockManager.tall_island_grass, 2, BlockTallIslandGrass.EnumType.GRASS.getMeta()));
        return ret;
    }

    public static enum EnumBlockHalf implements IStringSerializable
    {
        UPPER,
        LOWER;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == UPPER ? "upper" : "lower";
        }
    }

    public static enum EnumPlantType implements IStringSerializable
    {
        GRASS(0, "double_grass", "grass"),
        FERN(1, "double_fern", "fern");

        private static final BlockDoubleTallIslandGrass.EnumPlantType[] META_LOOKUP = new BlockDoubleTallIslandGrass.EnumPlantType[values().length];
        private final int meta;
        private final String name;
        private final String unlocalizedName;

        private EnumPlantType(int meta, String name)
        {
            this(meta, name, name);
        }

        private EnumPlantType(int meta, String name, String unlocalizedName)
        {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMeta()
        {
            return this.meta;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockDoubleTallIslandGrass.EnumPlantType byMetadata(int meta)
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

        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }

        static
        {
            for (BlockDoubleTallIslandGrass.EnumPlantType blockdoubleplant$enumplanttype : values())
            {
                META_LOOKUP[blockdoubleplant$enumplanttype.getMeta()] = blockdoubleplant$enumplanttype;
            }
        }
    }
}