package com.notvergin.jmr.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> JOHN_AMBIENT =
            registerSoundEvents("john_ambient");
    public static final RegistryObject<SoundEvent> JOHN_HURT =
            registerSoundEvents("john_hurt");
    public static final RegistryObject<SoundEvent> JOHN_DEATH =
            registerSoundEvents("john_death");
    public static final RegistryObject<SoundEvent> JOHN_EFFECT =
            registerSoundEvents("john_effect");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name)
    {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name)));
    }

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
