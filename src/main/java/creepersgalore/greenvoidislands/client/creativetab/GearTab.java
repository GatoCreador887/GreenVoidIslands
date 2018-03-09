package creepersgalore.greenvoidislands.client.creativetab;

import creepersgalore.greenvoidislands.common.core.GVIItemManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class GearTab extends CreativeTabs {

	public GearTab(String label) {
		super(label);
		
		
		
	}

	@Override
	public ItemStack getTabIconItem() {
		
		return new ItemStack(GVIItemManager.foligenu_and_obsidian);
		
	}

}
