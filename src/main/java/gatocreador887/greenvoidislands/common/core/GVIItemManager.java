package gatocreador887.greenvoidislands.common.core;

import java.util.ArrayList;
import java.util.List;

import gatocreador887.greenvoidislands.client.core.GVICreativeTabManager;
import gatocreador887.greenvoidislands.common.item.ItemFloatstoneShard;
import gatocreador887.greenvoidislands.common.item.ItemFoligenuAndObsidian;
import gatocreador887.greenvoidislands.common.item.ItemFuel;
import gatocreador887.greenvoidislands.common.item.ItemShronk;
import gatocreador887.greenvoidislands.common.item.ItemShronkSapling;
import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GVIItemManager {
	public static final List<Item> ITEMS = new ArrayList<>();
	
	public static final Item FOLIGENU = register(new ItemFuel(600), new ResourceLocation(GVIReference.ID, "foligenu"), GVICreativeTabManager.MATERIALS);
	public static final Item FOLIGENU_AND_OBSIDIAN = register(new ItemFoligenuAndObsidian(), new ResourceLocation(GVIReference.ID, "foligenu_and_obsidian"), GVICreativeTabManager.GEAR);
	public static final Item FOLIGENU_CHIP = register(new ItemFuel(67), new ResourceLocation(GVIReference.ID, "foligenu_chip"), GVICreativeTabManager.MATERIALS);
	public static final Item COMPRESSED_LEAVES = register(new Item(), new ResourceLocation(GVIReference.ID, "compressed_leaves"), GVICreativeTabManager.MATERIALS);
	public static final Item SHRONK = register(new ItemShronk(), new ResourceLocation(GVIReference.ID, "shronk"), GVICreativeTabManager.FOOD);
	public static final Item RAW_SHRONKER = register(new ItemFood(3, 0.3F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 400, 1), 0.85F), new ResourceLocation(GVIReference.ID, "raw_shronker"), GVICreativeTabManager.FOOD);
	public static final Item COOKED_SHRONKER = register(new ItemFood(8, 0.8F, true), new ResourceLocation(GVIReference.ID, "cooked_shronker"), GVICreativeTabManager.FOOD);
	public static final Item SHRONKER_FEATHER = register(new Item(), new ResourceLocation(GVIReference.ID, "shronker_feather"), GVICreativeTabManager.MATERIALS);
	public static final Item FLOATSTONE_SHARD = register(new ItemFloatstoneShard(), new ResourceLocation(GVIReference.ID, "floatstone_shard"), GVICreativeTabManager.MATERIALS);
	public static final Item FLOATSTONE_GEM = register(new Item(), new ResourceLocation(GVIReference.ID, "floatstone_gem"), GVICreativeTabManager.MATERIALS);
	
	static {
		registerItemBlock(GVIBlockManager.FOLIGENU_ORE);
		registerItemBlock(GVIBlockManager.FOLIGENU_BLOCK);
		registerItemBlock(GVIBlockManager.ISLAND_GRASS);
		registerItemBlock(GVIBlockManager.DEAD_ISLAND_GRASS);
		registerItemBlock(GVIBlockManager.TALL_ISLAND_GRASS);
		registerItemBlock(GVIBlockManager.DOUBLE_TALL_ISLAND_GRASS);
		registerItemBlock(GVIBlockManager.SHRONK_LOG);
		registerItemBlock(GVIBlockManager.SHRONK_LEAVES);
		registerItemBlock(GVIBlockManager.SHRONK_SAPLING, new ItemShronkSapling());
		registerItemBlock(GVIBlockManager.FLOATSTONE_SHARD_DIRT_ORE);
		registerItemBlock(GVIBlockManager.FLOATSTONE_SHARD_ORE);
	}
	
	public static Item register(Item itemIn, ResourceLocation name) {
		Item item = itemIn;
		item.setRegistryName(name);
		item.setUnlocalizedName(name.toString());
		ITEMS.add(item);
		
		return item;
	}
	
	public static Item register(Item itemIn, ResourceLocation name, CreativeTabs creativetab) {
		Item item = register(itemIn, name);
		item.setCreativeTab(creativetab);
		
		return item;
	}
	
	public static Item registerItemBlock(Block blockIn, Item itemIn) {
		Item item = register(itemIn, blockIn.getRegistryName());
		
		return item;
	}
	
	public static Item registerItemBlock(Block blockIn) {
		Item item = registerItemBlock(blockIn, new ItemBlock(blockIn));
		
		return item;
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		Item[] array = new Item[0];
		array = ITEMS.toArray(array);
		event.getRegistry().registerAll(array);
	}
	
	public static void registerRenders() {
		for (Item item : ITEMS) {
			registerRender(item);
		}
		
		registerRenderMeta(SHRONK, ItemShronk.RipenessType.STAGE1.getMetadata(), "shronk_stage1");
		registerRenderMeta(SHRONK, ItemShronk.RipenessType.STAGE2.getMetadata(), "shronk_stage2");
		registerRenderMeta(SHRONK, ItemShronk.RipenessType.STAGE3.getMetadata(), "shronk_stage3");
	}
	
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	public static void registerRenderMeta(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(GVIReference.ID + ":" + id, "inventory"));
	}
}
