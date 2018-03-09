package creepersgalore.greenvoidislands.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import creepersgalore.greenvoidislands.common.core.GVIItemManager;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockShronkLeaves extends BlockLeaves {
	
	public static final PropertyInteger FRUIT_AGE = PropertyInteger.create("fruit_age", 0, 3);
	
	public BlockShronkLeaves() {
		super();
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(FRUIT_AGE, 0).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.setRegistryName("shronk_leaves");
		
		this.leavesFancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
		
		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
	}
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		
		super.updateTick(worldIn, pos, state, rand);
		
		if ((worldIn.getBlockState(pos.west()).getMaterial() == Material.AIR ||
			worldIn.getBlockState(pos.east()).getMaterial() == Material.AIR ||
			worldIn.getBlockState(pos.north()).getMaterial() == Material.AIR ||
			worldIn.getBlockState(pos.south()).getMaterial() == Material.AIR ||
			worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR ||
			worldIn.getBlockState(pos.down()).getMaterial() == Material.AIR) &&
			state.getValue(FRUIT_AGE) < 3 && rand.nextInt(10) == 0) {
			
			worldIn.setBlockState(pos, state.withProperty(FRUIT_AGE, state.getValue(FRUIT_AGE) + 1));
			
		}
		
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (state.getValue(FRUIT_AGE) > 0)
		{
			world.setBlockState(pos, state.withProperty(FRUIT_AGE, 0), 3);
			state.getBlock().spawnAsEntity(world, pos.offset(side), new ItemStack(GVIItemManager.shronk, 1, state.getValue(FRUIT_AGE) - 1));
			return true;
		}

		return false;
	}
	
	protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1);
    }
	
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(StatList.getBlockStats(this));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }
	
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        ItemStack drop = new ItemStack(getItemDropped(state, rand, fortune), 1, 0);
        //if (!drop.isEmpty())
            //drops.add(drop);
        
        if (state.getValue(FRUIT_AGE) > 0) {
        	
        	drops.add(new ItemStack(GVIItemManager.shronk, 1, state.getValue(FRUIT_AGE) - 1));
        	
        }

        this.captureDrops(true);
        drops.addAll(this.captureDrops(false));
    }
	
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		
		NonNullList items = NonNullList.withSize(2, new ItemStack(this, 1, 0));
		
		if (world.getBlockState(pos).getValue(FRUIT_AGE) > 0) {
			
			items.add(new ItemStack(GVIItemManager.shronk, 1, world.getBlockState(pos).getValue(FRUIT_AGE) - 1));
			
		}
		
		return items;
		
	}
	
	public EnumType getWoodType(int meta) {
		
		return null;
		
	}
	
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FRUIT_AGE, (meta & 3) % 4).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }
	
	public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FRUIT_AGE);

        if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FRUIT_AGE, CHECK_DECAY, DECAYABLE});
    }
	
}
