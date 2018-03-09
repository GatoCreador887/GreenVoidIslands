package creepersgalore.greenvoidislands.common.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GVISoundManager {
	
	public static final List<SoundEvent> SOUNDS = new ArrayList<>();
	
	public static final SoundEvent gvi_music = register(new ResourceLocation("greenvoidislands:gvi_music"));
	public static final SoundEvent gvi_portal = register(new ResourceLocation("greenvoidislands:gvi_portal"));
	public static final SoundEvent gvi_travel = register(new ResourceLocation("greenvoidislands:gvi_travel"));
	public static final SoundEvent ambient_reverb = register(new ResourceLocation("greenvoidislands:ambient_reverb"));
	
	public static SoundEvent register(ResourceLocation name) {
		
		SoundEvent event = new SoundEvent(name);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		SOUNDS.add(event);
		return event;
		
	}

	public static void preInit(FMLPreInitializationEvent event) {
		
		assert !SOUNDS.isEmpty();
		
	}
	
}
