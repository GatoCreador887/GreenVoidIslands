package gatocreador887.greenvoidislands;

import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = GVIReference.ID)
@Config.LangKey("greenvoidislands.config.title")
public class GVIConfig {
	public static int dimensionId = 7;
	
	public static final Generation GENERATION = new Generation();
	
	public static class Generation {
		public Biomes biomes = new Biomes();
		
		public static class Biomes {
			public boolean plainsIsland = true;
			public boolean barrenIsland = true;
			public boolean forestIsland = true;
		}
	}
	
	@Mod.EventBusSubscriber(modid = GVIReference.ID)
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(GVIReference.ID)) {
				ConfigManager.sync(GVIReference.ID, Config.Type.INSTANCE);
				
				if (!GENERATION.biomes.plainsIsland && !GENERATION.biomes.barrenIsland && !GENERATION.biomes.forestIsland) {
					Minecraft.getMinecraft().getToastGui().add(new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, new TextComponentString(I18n.translateToLocal("greenvoidislands.config.nobiomesalert.title")),  new TextComponentString(I18n.translateToLocal("greenvoidislands.config.nobiomesalert.subtitle"))));
					GENERATION.biomes.plainsIsland = true;
					GENERATION.biomes.barrenIsland = true;
					GENERATION.biomes.forestIsland = true;
					ConfigManager.sync(GVIReference.ID, Config.Type.INSTANCE);
				}
			}
		}
	}
}