package com.notvergin.jmr.mobeffects;

import com.notvergin.jmr.mobeffects.customeffects.ImmortalityEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class ModMobEffects
{
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

    public static final RegistryObject<ImmortalityEffect> IMMORTAL_EFFECT = MOB_EFFECTS.register("immortal",
            () -> new ImmortalityEffect(MobEffectCategory.BENEFICIAL, 0X1c0508));

    public static void register(IEventBus bus)
    {
        MOB_EFFECTS.register(bus);
    }
}
