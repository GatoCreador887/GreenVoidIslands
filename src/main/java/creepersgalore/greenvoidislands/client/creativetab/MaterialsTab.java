package creepersgalore.greenvoidislands.client.creativetab;

import creepersgalore.greenvoidislands.common.core.GVIItemManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MaterialsTab extends CreativeTabs {

	public MaterialsTab(String label) {
		super(label);
		
		
		
	}

	@Override
	public ItemStack getTabIconItem() {
		
		return new ItemStack(GVIItemManager.foligenu);
		
	}

}
