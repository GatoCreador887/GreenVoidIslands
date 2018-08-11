package gatocreador887.greenvoidislands.client.creativetab;

import gatocreador887.greenvoidislands.common.core.GVIItemManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class GearTab extends CreativeTabs {
	
	public GearTab(String label) {
		super(label);
		
	}
	
	@Override
	public ItemStack getTabIconItem() {
		
		return new ItemStack(GVIItemManager.FOLIGENU_AND_OBSIDIAN);
		
	}
	
}
