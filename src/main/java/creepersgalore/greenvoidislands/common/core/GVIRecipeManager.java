package creepersgalore.greenvoidislands.common.core;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GVIRecipeManager {
	
	/*public static void crafting() {
		
		GameRegistry.addShapelessRecipe(new ItemStack(GVIItemManager.foligenu_and_obsidian), new Object[]{GVIItemManager.foligenu, Blocks.OBSIDIAN});
		GameRegistry.addRecipe(new ItemStack(GVIBlockManager.foligenu_block), new Object[]{"FFF", "FFF", "FFF", 'F', GVIItemManager.foligenu});
		GameRegistry.addShapelessRecipe(new ItemStack(GVIItemManager.foligenu, 9), new Object[]{GVIBlockManager.foligenu_block});
		
	}*/
	
	//Was 'smelting'.
	public static void preInit(FMLPreInitializationEvent event) {
		
		GameRegistry.addSmelting(GVIBlockManager.foligenu_ore, new ItemStack(GVIItemManager.foligenu), 0.2F);
		
	}
	
}
