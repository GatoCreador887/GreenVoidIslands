package gatocreador887.greenvoidislands.common.core;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GVIRecipeManager {
	public static void init() {
		FurnaceRecipes.instance().addSmeltingRecipeForBlock(GVIBlockManager.FOLIGENU_ORE, new ItemStack(GVIItemManager.FOLIGENU), 0.55F);
		FurnaceRecipes.instance().addSmelting(GVIItemManager.RAW_SHRONKER, new ItemStack(GVIItemManager.COOKED_SHRONKER), 0.35F);
	}
}
