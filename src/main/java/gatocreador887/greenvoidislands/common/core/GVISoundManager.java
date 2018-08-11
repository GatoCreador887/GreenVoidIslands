package gatocreador887.greenvoidislands.common.core;

import java.util.ArrayList;
import java.util.List;

import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class GVISoundManager {
	public static final List<SoundEvent> SOUNDS = new ArrayList<>();
	
	public static final SoundEvent GVI_MUSIC = register(new ResourceLocation(GVIReference.ID, "gvi_music"));
	public static final SoundEvent GVI_PORTAL = register(new ResourceLocation(GVIReference.ID, "gvi_portal"));
	public static final SoundEvent GVI_TRAVEL = register(new ResourceLocation(GVIReference.ID, "gvi_travel"));
	public static final SoundEvent AMBIENT_REVERB = register(new ResourceLocation(GVIReference.ID, "ambient_reverb"));
	
	public static SoundEvent register(ResourceLocation name) {
		SoundEvent event = new SoundEvent(name);
		event.setRegistryName(name);
		SOUNDS.add(event);
		
		return event;
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		SoundEvent[] array = new SoundEvent[0];
		array = SOUNDS.toArray(array);
		event.getRegistry().registerAll(array);
	}
}
