package gatocreador887.greenvoidislands.common.core;

import java.util.ArrayList;
import java.util.List;

import gatocreador887.greenvoidislands.client.core.GVICreativeTabManager;
import gatocreador887.greenvoidislands.common.block.BlockDeadIslandGrass;
import gatocreador887.greenvoidislands.common.block.BlockDoubleTallIslandGrass;
import gatocreador887.greenvoidislands.common.block.BlockFloatstoneShardDirtOre;
import gatocreador887.greenvoidislands.common.block.BlockFloatstoneShardOre;
import gatocreador887.greenvoidislands.common.block.BlockFoliageFire;
import gatocreador887.greenvoidislands.common.block.BlockFoligenu;
import gatocreador887.greenvoidislands.common.block.BlockFoligenuOre;
import gatocreador887.greenvoidislands.common.block.BlockGVIPortal;
import gatocreador887.greenvoidislands.common.block.BlockIslandGrass;
import gatocreador887.greenvoidislands.common.block.BlockShronkLeaves;
import gatocreador887.greenvoidislands.common.block.BlockShronkLog;
import gatocreador887.greenvoidislands.common.block.BlockShronkSapling;
import gatocreador887.greenvoidislands.common.block.BlockTallIslandGrass;
import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GVIBlockManager {
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	public static final Block FOLIGENU_ORE = register(new BlockFoligenuOre(), new ResourceLocation(GVIReference.ID, "foligenu_ore"), GVICreativeTabManager.MATERIALS);
	public static final Block FOLIGENU_BLOCK = register(new BlockFoligenu(), new ResourceLocation(GVIReference.ID, "foligenu_block"), GVICreativeTabManager.MATERIALS);
	public static final Block FOLIAGE_FIRE = register(new BlockFoliageFire(), new ResourceLocation(GVIReference.ID, "foliage_fire"));
	public static final Block GVI_PORTAL = register(new BlockGVIPortal(), new ResourceLocation(GVIReference.ID, "gvi_portal"));
	public static final Block ISLAND_GRASS = register(new BlockIslandGrass(), new ResourceLocation(GVIReference.ID, "island_grass"), GVICreativeTabManager.MATERIALS);
	public static final Block DEAD_ISLAND_GRASS = register(new BlockDeadIslandGrass(), new ResourceLocation(GVIReference.ID, "dead_island_grass"), GVICreativeTabManager.MATERIALS);
	public static final Block TALL_ISLAND_GRASS = register(new BlockTallIslandGrass(), new ResourceLocation(GVIReference.ID, "tall_island_grass"), GVICreativeTabManager.MATERIALS);
	public static final Block DOUBLE_TALL_ISLAND_GRASS = register(new BlockDoubleTallIslandGrass(), new ResourceLocation(GVIReference.ID, "double_tall_island_grass"), GVICreativeTabManager.MATERIALS);
	public static final Block SHRONK_LOG = register(new BlockShronkLog(), new ResourceLocation(GVIReference.ID, "shronk_log"), GVICreativeTabManager.MATERIALS);
	public static final Block SHRONK_LEAVES = register(new BlockShronkLeaves(), new ResourceLocation(GVIReference.ID, "shronk_leaves"), GVICreativeTabManager.MATERIALS);
	public static final Block SHRONK_SAPLING = register(new BlockShronkSapling(), new ResourceLocation(GVIReference.ID, "shronk_sapling"), GVICreativeTabManager.MATERIALS);
	public static final Block FLOATSTONE_SHARD_DIRT_ORE = register(new BlockFloatstoneShardDirtOre(), new ResourceLocation(GVIReference.ID, "floatstone_shard_dirt_ore"), GVICreativeTabManager.MATERIALS);
	public static final Block FLOATSTONE_SHARD_ORE = register(new BlockFloatstoneShardOre(), new ResourceLocation(GVIReference.ID, "floatstone_shard_ore"), GVICreativeTabManager.MATERIALS);
	
	static {
		BlockFoliageFire.init();
	}
	
	public static Block register(Block blockIn, ResourceLocation name) {
		Block block = blockIn;
		block.setRegistryName(name);
		block.setUnlocalizedName(name.toString());
		BLOCKS.add(block);
		
		return block;
	}
	
	public static Block register(Block blockIn, ResourceLocation name, CreativeTabs creativetab) {
		Block block = register(blockIn, name);
		block.setCreativeTab(creativetab);
		
		return block;
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		Block[] array = new Block[0];
		array = BLOCKS.toArray(array);
		event.getRegistry().registerAll(array);
	}
	
	public static void registerRenders() {
		for (Block block : BLOCKS) {
			registerRender(block);
		}
	}
	
	public static void registerRender(Block block) {
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
