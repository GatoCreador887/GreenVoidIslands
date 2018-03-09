package creepersgalore.greenvoidislands.common.core;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import creepersgalore.greenvoidislands.client.core.GVICreativeTabManager;
import creepersgalore.greenvoidislands.common.block.BlockDeadIslandGrass;
import creepersgalore.greenvoidislands.common.block.BlockDoubleTallIslandGrass;
import creepersgalore.greenvoidislands.common.block.BlockFoliageFire;
import creepersgalore.greenvoidislands.common.block.BlockFoligenu;
import creepersgalore.greenvoidislands.common.block.BlockFoligenuOre;
import creepersgalore.greenvoidislands.common.block.BlockGVIPortal;
import creepersgalore.greenvoidislands.common.block.BlockIslandGrass;
import creepersgalore.greenvoidislands.common.block.BlockShronkLeaves;
import creepersgalore.greenvoidislands.common.block.BlockShronkLog;
import creepersgalore.greenvoidislands.common.block.BlockShronkSapling;
import creepersgalore.greenvoidislands.common.block.BlockTallIslandGrass;
import creepersgalore.greenvoidislands.util.Reference;

public class GVIBlockManager {
	
	public static Block foligenu_ore;
	public static Block foligenu_block;
	public static Block foliage_fire;
	public static Block gvi_portal;
	public static Block island_grass;
	public static Block dead_island_grass;
	public static Block tall_island_grass;
	public static Block double_tall_island_grass;
	public static Block shronk_log; 
	public static Block shronk_leaves;
	public static Block shronk_sapling;
	
	public static void preInit(FMLPreInitializationEvent event) {
		
		foligenu_ore = new BlockFoligenuOre().setUnlocalizedName("foligenu_ore");
		foligenu_block = new BlockFoligenu().setCreativeTab(GVICreativeTabManager.materials);
		foliage_fire = new BlockFoliageFire().setUnlocalizedName("foliage_fire").setRegistryName("foliage_fire");
		gvi_portal = new BlockGVIPortal().setUnlocalizedName("gvi_portal").setRegistryName("gvi_portal");
		island_grass = new BlockIslandGrass().setUnlocalizedName("island_grass");
		dead_island_grass = new BlockDeadIslandGrass().setUnlocalizedName("dead_island_grass");
		tall_island_grass = new BlockTallIslandGrass().setUnlocalizedName("tall_island_grass");
		double_tall_island_grass = new BlockDoubleTallIslandGrass().setUnlocalizedName("double_tall_island_grass");
		shronk_log = new BlockShronkLog().setUnlocalizedName("shronk_log");
		shronk_leaves = new BlockShronkLeaves().setUnlocalizedName("shronk_leaves");
		shronk_sapling = new BlockShronkSapling().setUnlocalizedName("shronk_sapling");
		
		ForgeRegistries.BLOCKS.register(foligenu_ore);
		ForgeRegistries.BLOCKS.register(foligenu_block);
		ForgeRegistries.BLOCKS.register(foliage_fire);
		ForgeRegistries.BLOCKS.register(gvi_portal);
		ForgeRegistries.BLOCKS.register(island_grass);
		ForgeRegistries.BLOCKS.register(dead_island_grass);
		ForgeRegistries.BLOCKS.register(tall_island_grass);
		ForgeRegistries.BLOCKS.register(double_tall_island_grass);
		ForgeRegistries.BLOCKS.register(shronk_log);
		ForgeRegistries.BLOCKS.register(shronk_leaves);
		ForgeRegistries.BLOCKS.register(shronk_sapling);
		
	}
	
	public static void registerRenders() {
		
		registerRender(foligenu_ore);
		registerRender(foligenu_block);
		registerRender(foliage_fire);
		registerRender(gvi_portal);
		registerRender(island_grass);
		registerRender(dead_island_grass);
		registerRender(tall_island_grass);
		registerRender(double_tall_island_grass);
		registerRender(shronk_log);
		registerRender(shronk_leaves);
		registerRender(shronk_sapling);
		
	}
	
	public static void registerRender(Block block) {
		
		Item item = Item.getItemFromBlock(block);
		
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		
	}
	
}
