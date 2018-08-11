package gatocreador887.greenvoidislands.client.creativetab;

import gatocreador887.greenvoidislands.common.core.GVIItemManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MaterialsTab extends CreativeTabs {
	
	public MaterialsTab(String label) {
		super(label);
		
	}
	
	@Override
	public ItemStack getTabIconItem() {
		
		return new ItemStack(GVIItemManager.FOLIGENU);
		
	}
	
}
