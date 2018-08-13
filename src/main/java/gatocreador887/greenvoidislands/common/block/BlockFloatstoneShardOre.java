package gatocreador887.greenvoidislands.common.block;

import java.util.Random;

import gatocreador887.greenvoidislands.client.core.GVICreativeTabManager;
import gatocreador887.greenvoidislands.common.core.GVIItemManager;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockFloatstoneShardOre extends BlockOre {
	public BlockFloatstoneShardOre() {
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return GVIItemManager.FLOATSTONE_SHARD;
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 1 + random.nextInt(3);
	}
	
	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
		Random rand = world instanceof World ? ((World) world).rand : new Random();
		
		return MathHelper.getInt(rand, 2, 5);
	}
}
