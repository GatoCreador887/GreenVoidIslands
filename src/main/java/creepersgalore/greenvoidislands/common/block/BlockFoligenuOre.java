package creepersgalore.greenvoidislands.common.block;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import creepersgalore.greenvoidislands.client.core.GVICreativeTabManager;
import creepersgalore.greenvoidislands.common.core.GVIItemManager;

public class BlockFoligenuOre extends BlockOre {
	
	public BlockFoligenuOre() {
		
		this.setCreativeTab(GVICreativeTabManager.materials);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel("pickaxe", 1);
		this.setRegistryName("foligenu_ore");
		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return GVIItemManager.foligenu;
		
	}
	
	public int quantityDropped(Random random) {
		
		return 1 + random.nextInt(3);
		
	}
	
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
		
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		
		return MathHelper.getInt(rand, 1, 4);
		
	}
	
}
