package creepersgalore.greenvoidislands.common.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import creepersgalore.greenvoidislands.client.core.GVICreativeTabManager;
import creepersgalore.greenvoidislands.common.item.ItemFoligenuAndObsidian;
import creepersgalore.greenvoidislands.common.item.ItemFuel;
import creepersgalore.greenvoidislands.common.item.ItemShronk;
import creepersgalore.greenvoidislands.util.Reference;

public class GVIItemManager {
	
	public static Item foligenu;
	public static Item foligenu_and_obsidian;
	public static Item foligenu_chip;
	public static Item compressed_leaves;
	public static Item shronk;
	
	public static void preInit(FMLPreInitializationEvent event) {
		
		foligenu = new ItemFuel(600).setUnlocalizedName("foligenu").setRegistryName("foligenu").setCreativeTab(GVICreativeTabManager.materials);
		foligenu_and_obsidian = new ItemFoligenuAndObsidian().setUnlocalizedName("foligenu_and_obsidian").setRegistryName("foligenu_and_obsidian");
		foligenu_chip = new ItemFuel(67).setUnlocalizedName("foligenu_chip").setRegistryName("foligenu_chip").setCreativeTab(GVICreativeTabManager.materials);
		compressed_leaves = new Item().setUnlocalizedName("compressed_leaves").setRegistryName("compressed_leaves").setCreativeTab(GVICreativeTabManager.materials);
		shronk = new ItemShronk().setUnlocalizedName("shronk").setRegistryName("shronk");
		
		ForgeRegistries.ITEMS.register(foligenu);
		ForgeRegistries.ITEMS.register(foligenu_and_obsidian);
		ForgeRegistries.ITEMS.register(foligenu_chip);
		ForgeRegistries.ITEMS.register(compressed_leaves);
		ForgeRegistries.ITEMS.register(shronk);
		
	}
	
	public static void registerRenders() {
		
		registerRender(foligenu);
		registerRender(foligenu_and_obsidian);
		registerRender(foligenu_chip);
		registerRender(compressed_leaves);
		registerRenderMeta(shronk, ItemShronk.RipenessType.STAGE1.getMetadata(), "shronk_stage1");
		registerRenderMeta(shronk, ItemShronk.RipenessType.STAGE2.getMetadata(), "shronk_stage2");
		registerRenderMeta(shronk, ItemShronk.RipenessType.STAGE3.getMetadata(), "shronk_stage3");
		
	}
	
	public static void registerRender(Item item) {//XXX 772 706
		
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		
	}
	
	public static void registerRenderMeta(Item item, int meta, String id) {
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.ID + ":" + id, "inventory"));
		
	}
	
}
