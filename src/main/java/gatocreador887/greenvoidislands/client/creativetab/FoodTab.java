package gatocreador887.greenvoidislands.client.creativetab;

import gatocreador887.greenvoidislands.common.core.GVIItemManager;
import gatocreador887.greenvoidislands.common.item.ItemShronk;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FoodTab extends CreativeTabs {
	public FoodTab(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(GVIItemManager.SHRONK, 1, ItemShronk.RipenessType.STAGE3.getMetadata());
	}
}
